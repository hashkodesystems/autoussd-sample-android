import java.net.URI

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
    // 1. Add the AutoUssd SDK Maven repository
    maven{ url = uri("https://raw.github.com/hashkodesystems/autoussd-mvn/master") }
  }
}

rootProject.name = "Android Sample"
include(":app")
 