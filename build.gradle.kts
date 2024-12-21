import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    `java-library`
    jacoco
    idea
    signing
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.dokka)
    alias(libs.plugins.maven.publish)
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
        // not support snapshot version neither GithubPackages or MavenCentral
        // so, use 'SNAPSHOT.<NUM>' as version for GithubPackages
        "$baseVersion-SNAPSHOT${System.getenv("GITHUB_RUN_NUMBER")?.let { ".$it" } ?: ""}"
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
/************************************************************
 * Publishing configuration
 ************************************************************/
// signing configuration
signing {
    val signingKey = System.getenv("GPG_PRIVATE_KEY") // for GA
        ?: project.findProperty("signing.keyFile") // for local (gpg 2.x)
            ?.let(::file)?.readText()
    val signingPassword = System.getenv("GPG_PASSPHRASE") // for GA
        ?: project.findProperty("signing.password") as String? // for local
    if (signingKey != null && signingPassword != null) {
        useInMemoryPgpKeys(
            /* defaultSecretKey = */ signingKey,
            /* defaultPassword = */ signingPassword
        )
    }
    sign(publishing.publications)
}
// for maven central publication
mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    configure(KotlinJvm(javadocJar = JavadocJar.Dokka("dokkaHtml"), sourcesJar = true))
    coordinates(
        groupId = group.toString(),
        artifactId = project.name,
        version = version.toString()
    )
    pom { configureDefault() }
}

// for github package publication (use: maven publication)
publishing {
    repositories {
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/steamfunc/kotlin-expect")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: project.findProperty("github.username") as String?
                password = System.getenv("GITHUB_TOKEN") ?: project.findProperty("github.password") as String?
            }
        }
    }
}
/**
 * default configuration for maven publication
 */
fun MavenPom.configureDefault() {
    name = project.name
    description = project.description
    url = "https://github.com/steamfunc/kotlin-expect"
    licenses {
        license {
            name = "The Apache Software License, Version 2.0"
            url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
        }
    }
    developers {
        developer {
            id = "steamfunc"
            name = "Yunsang Choi"
            email = "code.aznable@gmail.com"
        }
    }
    scm {
        url = "https://github.com/steamfunc/kotlin-expect"
    }
}

// publish to Github Packages repository (task shortcut)
tasks.register("publishToGithub") {
    description = "Publishes the maven publication to Github Packages repository"
    group = "release"
    dependsOn("publishMavenPublicationToGithubPackagesRepository")
}
// Task: publishToMavenCentral
// publish to Maven Central repository (task shortcut)
// already defined in maven-publishing block

