import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    `java-library`
    `maven-publish`
    jacoco
    idea
    signing
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.nexus.publish)
}

group = "io.github.steamfunc"
version = findProperty("project.version") as String
description = "rspec style assertion library for kotlin test"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly(kotlin("reflect"))
    api(libs.slf4j.api)

    // testing
    testRuntimeOnly(kotlin("stdlib-jdk8"))
    testRuntimeOnly(kotlin("reflect"))
    testApi(kotlin("test"))
    testApi(kotlin("test-junit5"))
    testApi(libs.junit5.all)
    testApi(libs.junit5.params)
    testRuntimeOnly(libs.junit5.platform.launcher)
    testRuntimeOnly(libs.logback.classic)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    withJavadocJar()
    withSourcesJar()
}

kotlin {
    explicitApi()
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
        apiVersion.set(KotlinVersion.KOTLIN_1_9)
        languageVersion.set(KotlinVersion.KOTLIN_1_9)
        freeCompilerArgs = listOf("-Xjsr305=strict", "-opt-in=kotlin.RequiresOptIn")
    }
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
    jacoco {
        toolVersion = libs.versions.jacoco.tool.get()
    }
    jacocoTestReport {
        reports {
            html.required.set(true)
            html.outputLocation.set(layout.buildDirectory.dir("jacoco/coverage/"))
            xml.required.set(true)
            xml.outputLocation.set(layout.buildDirectory.file("jacoco/coverage.xml"))
        }
    }
    withType<Test> {
        finalizedBy(jacocoTestReport)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            pom {
                name.set(project.name)
                description.set(project.description)
                url.set("https://github.com/odd-poet/kotlin-expect")
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("oddpoet")
                        name.set("Yunsang Choi")
                        email.set("oddpoet@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/odd-poet/kotlin-expect")
                }
            }
        }
    }
}

nexusPublishing {
    repositories {
        sonatype()
    }
}

signing {
    sign(publishing.publications["maven"])
}

