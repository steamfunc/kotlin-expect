rootProject.name = "kotlin-expect"

/**
 * gradle plugin management.
 *
 */

buildscript {
    repositories {
        mavenCentral()
    }
}
// version catalog
dependencyResolutionManagement {
    versionCatalogs {

        create("libs") {
            version("kotlin", settings.extra["kotlin.version"] as String)
            version("slf4j", settings.extra["slf4j.version"] as String)
            version("logback", settings.extra["logback.version"] as String)
            version("junit5", settings.extra["junit5.version"] as String)
            version("junit5-platform", settings.extra["junit5.platform.version"] as String)
            version("jacoco-tool", settings.extra["jacoco.version"] as String)

            // plugins
            plugin("kotlin.jvm", "org.jetbrains.kotlin.jvm")
                .versionRef("kotlin")

            // libraries
            library("slf4j-api", "org.slf4j", "slf4j-api")
                .versionRef("slf4j")

            library("logback-classic", "ch.qos.logback", "logback-classic")
                .versionRef("logback")

            library("junit5-all", "org.junit.jupiter", "junit-jupiter")
                .versionRef("junit5")

            library("junit5-api", "org.junit.jupiter", "junit-jupiter-api")
                .versionRef("junit5")

            library("junit5-params", "org.junit.jupiter", "junit-jupiter-params")
                .versionRef("junit5")

            library("junit5-engine", "org.junit.jupiter", "junit-jupiter-engine")
                .versionRef("junit5")

            library("junit5-platform-launcher", "org.junit.platform", "junit-platform-launcher")
                .versionRef("junit5-platform")
        }
    }
}
