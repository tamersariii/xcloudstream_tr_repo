pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
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
