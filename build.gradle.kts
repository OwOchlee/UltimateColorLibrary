plugins {
    kotlin("jvm") version "1.8.21"
    id("org.jetbrains.dokka") version "1.8.20"
}

repositories {
    mavenCentral()
}

tasks.dokkaHtml {
    outputDirectory.set(file(System.getenv("DOKKA_OUTPUT")))
}