import com.android.build.gradle.BaseExtension
import com.lagradost.cloudstream3.gradle.CloudstreamExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        // AGP 8.7.3 → minimum Gradle 8.9 gerektirir
        classpath("com.android.tools.build:gradle:8.7.3")
        // CloudStream Gradle eklentisi (makePluginsJson vb. görevleri sağlar)
        classpath("com.github.recloudstream:gradle:-SNAPSHOT")
        // Kotlin 2.4.0 → CloudStream kütüphanesinin 2.4.0 metadata'sını okuyabilir
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.4.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

// extensions.getByName() "Any" döndüğü için açıkça cast edip .apply() ile lambda'yı çalıştırıyoruz.
fun Project.cloudstream(configuration: CloudstreamExtension.() -> Unit) =
    (extensions.getByName("cloudstream") as CloudstreamExtension).apply(configuration)

fun Project.android(configuration: BaseExtension.() -> Unit) =
    (extensions.getByName("android") as BaseExtension).apply(configuration)

subprojects {
    // Tüm alt modüllere gerekli eklentileri uygula
    apply(plugin = "com.android.library")
    apply(plugin = "kotlin-android")
    apply(plugin = "com.lagradost.cloudstream3.gradle")

    cloudstream {
        setRepo(System.getenv("GITHUB_REPOSITORY") ?: "tamersariii/xcloudstream_tr_repo")
    }

    android {
        // Her modül için otomatik namespace (örn: com.xcloudstream.hdfilmcehennemi)
        namespace = "com.xcloudstream.${project.name.lowercase().replace("-", "")}"
        compileSdkVersion(35)

        defaultConfig {
            minSdk = 21
            targetSdk = 35
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }

    tasks.withType<KotlinJvmCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            freeCompilerArgs.addAll(
                "-Xno-call-assertions",
                "-Xno-param-assertions",
                "-Xno-receiver-assertions"
            )
        }
    }

    dependencies {
        val cloudstream by configurations
        val implementation by configurations

        // CloudStream API stub'ı (derleme zamanı için)
        cloudstream("com.lagradost:cloudstream3:pre-release")

        implementation(kotlin("stdlib"))
        implementation("com.github.Blatzar:NiceHttp:0.4.11")
        implementation("org.jsoup:jsoup:1.18.3")
        // Jackson sürümünü 2.13.1'in üzerine çıkarmayın, eski Android cihazlarda uyumsuzluk yaratır.
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")

        // ✅ JSpecify anotasyonları — CloudStream kütüphanesinin @Nullable anotasyonları için gerekli
        implementation("org.jspecify:jspecify:1.0.0")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
