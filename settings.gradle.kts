pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "Parchment"

include("Parchment-Common", "Parchment-API", "Parchment-Server")
