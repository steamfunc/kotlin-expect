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
            version("kotlin", "1.9.23")
            version("gradle-nexus-publish-plugin", "1.1.0")
            version("slf4j", "1.7.36")
            version("logback", "1.2.11")
            version("junit5", "5.9.3")
            version("junit5-platform", "1.9.3")
            version("jacoco-tool", "0.8.12")

            // plugins
            plugin("kotlin.jvm", "org.jetbrains.kotlin.jvm")
                .versionRef("kotlin")

            plugin("nexus-publish", "io.github.gradle-nexus.publish-plugin")
                .versionRef("gradle-nexus-publish-plugin")

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
