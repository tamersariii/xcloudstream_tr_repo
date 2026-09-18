import com.android.build.gradle.BaseExtension
import com.lagradost.cloudstream3.gradle.CloudstreamExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io") // CloudStream araçları ve bağımlılıkları için
    }
    dependencies {
        // Resmi CloudStream deposuyla uyumlu sürümler
        classpath("com.android.tools.build:gradle:8.7.3")
        classpath("com.github.recloudstream:gradle:-SNAPSHOT")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

// CloudStream ve Android extension'larına kolay erişim için yardımcı fonksiyonlar
fun Project.cloudstream(configuration: CloudstreamExtension.() -> Unit) =
    extensions.getByName("cloudstream").configuration()

fun Project.android(configuration: BaseExtension.() -> Unit) =
    extensions.getByName("android").configuration()

subprojects {
    // Tüm alt modüllere gerekli eklentileri uygula
    apply(plugin = "com.android.library")
    apply(plugin = "kotlin-android")
    apply(plugin = "com.lagradost.cloudstream3.gradle")

    cloudstream {
        // GitHub Actions ortamında depo adını otomatik alır
        setRepo(System.getenv("GITHUB_REPOSITORY") ?: "tamersariii/xcloudstream_tr_repo")
    }

    android {
        // Her modül için otomatik namespace (örneğin: com.xcloudstream.hdfilmcehennemi)
        namespace = "com.xcloudstream.${project.name.lowercase().replace("-", "")}"
        compileSdkVersion(35)

        defaultConfig {
            minSdk = 21
            targetSdk = 35
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }

    tasks.withType<KotlinJvmCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
            // CloudStream eklentileri için gerekli derleyici argümanları
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
        implementation("com.github.Blatzar:NiceHttp:0.4.11") // HTTP istemcisi
        implementation("org.jsoup:jsoup:1.18.3") // HTML ayrıştırıcı
        // Jackson sürümünü 2.13.1'in üzerine çıkarmayın, eski Android cihazlarda uyumsuzluk yaratır.
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
