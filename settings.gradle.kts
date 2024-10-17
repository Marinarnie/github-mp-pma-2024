pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "PMA"
include(":myapp01linearlayout")
include(":myapp002myownlinearlayout2")
include(":myapp004objednavka")
include(":myapp005jetpackcompose")
include(":myapp006moreactivities2")
include(":myapp007toastsnackbar")
