plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    application
    signing
    `maven-publish`
}

version = parent?.version ?: "?-SNAPSHOT"
group = "app.moreo.ucl"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    implementation("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
    api(project(":ultimate-color-library-minecraft"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

tasks.register<Jar>("dokkaJavadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

publishing {
    repositories {
        maven {
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/releases/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

            credentials {
                username = System.getenv("SONATYPE_USERNAME")
                password = System.getenv("SONATYPE_PASSWORD")
            }
        }
    }
    publications {
        create<MavenPublication>("mavenKotlin") {
            artifactId = "ultimate-color-library-bukkit"
            groupId = "app.moreo"
            version = project.version.toString()

            artifact(tasks.getByName("dokkaJavadocJar"))
            artifact(tasks.kotlinSourcesJar)

            from(components["kotlin"])
            pom {
                name.set("Ultimate Color Library")
                description.set("A library for color manipulation and interpolation")
                url.set("https://github.com/MoreOwO/UltimateColorLibrary")
                packaging = "jar"

                licenses {
                    license {
                        name.set("Creative Commons Attribution-ShareAlike 4.0 International")
                        url.set("https://creativecommons.org/licenses/by-sa/4.0/")
                    }
                }

                developers {
                    developer {
                        id.set("moreowo")
                        name.set("MoréOwO")
                        email.set("main@moreo.app")
                        url.set("https://github.com/MoreOwO")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/MoreOwO/UltimateColorLibrary.git")
                    developerConnection.set("scm:git:git@github.com:MoreOwO/UltimateColorLibrary.git")
                    url.set("https://github.com/MoreOwO/UltimateColorLibrary")
                }
            }
        }
    }
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

signing {
    useGpgCmd()
    sign(publishing.publications["mavenKotlin"])
}
