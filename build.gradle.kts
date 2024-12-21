import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    `java-library`
    `maven-publish`
    jacoco
    idea
    signing
    alias(libs.plugins.kotlin.jvm)
}

group = "io.github.steamfunc"
version = determineVersion()
description = "rspec style assertion library for kotlin test"

fun determineVersion(): String {
    val baseVersion = findProperty("project.version") as String
    val refName = System.getenv("GITHUB_REF_NAME")
    val refType = System.getenv("GITHUB_REF_TYPE")
    return if (refType == "branch" && refName == "main") {
        baseVersion
    } else {
        "$baseVersion-SNAPSHOT"
    }
}

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
    repositories {
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/steamfunc/kotlin-expect")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
//            groupId = project.group.toString()
//            artifactId = project.name
//            version = project.version.toString()
            pom {
                name.set(project.name)
                description.set(project.description)
                url.set("https://github.com/steamfunc/kotlin-expect")
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("steamfunc")
                        name.set("Yunsang Choi")
                        email.set("code.aznable@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/steamfunc/kotlin-expect")
                }
            }
        }
    }
}

//nexusPublishing {
//    repositories {
//        sonatype()
//    }
//}
//
//signing {
//    sign(publishing.publications["maven"])
//}
//
