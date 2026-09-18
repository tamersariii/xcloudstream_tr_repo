pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}

dependencyResolutionManagement {
    // PREFER_SETTINGS yerine PREFER_PROJECT kullanıyoruz
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

rootProject.name = "xcloudstream_tr_repo"

include(":HDFilmCehennemi")
include(":FullHDFilmizlesene")
include(":FilmMakinesi")
include(":UltraFilmizle")
include(":HDFilmizle")
include(":FilmKutusu")
include(":HDFilmizlesende")
