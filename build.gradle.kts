import ca.solostudios.nyx.util.codeMC
import ca.solostudios.nyx.util.reposiliteMaven

plugins {
    `java-library`
    `maven-publish`

    alias(libs.plugins.nyx)
    alias(libs.plugins.axion.release)
}

nyx {
    info {
        name = "Seismic"
        group = "com.dfsek"
        module = "seismic"
        version = scmVersion.version
        description = """
            Seismic is a Java sampler, math, type, and utility library.
        """.trimIndent()

        organizationName = "Polyhedral Development"
        organizationUrl = "https://github.com/PolyhedralDev/"

        repository.fromGithub("PolyhedralDev", "Seismic")

        license.useLGPLv3()

        developer {
            id = "duplexsys"
            name = "Zoë Gidiere"
            email = "duplexsys@protonmail.com"
            url = "https://github.com/duplexsystem"
        }
    }

    compile {
        jvmTarget = 21

        javadocJar = true
        sourcesJar = true

        allWarnings = true
        distributeLicense = true
        buildDependsOnJar = true
        reproducibleBuilds = true

        java {
            allJavadocWarnings = true
            noMissingJavadocWarnings = true
            javadocWarningsAsErrors = true
        }
    }

    publishing {
        withSignedPublishing()

        repositories {
            maven {
                name = "Sonatype"

                val repositoryId: String? by project
                url = when {
                    repositoryId != null -> uri("https://s01.oss.sonatype.org/service/local/staging/deployByRepositoryId/$repositoryId/")
                    else -> uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                }

                credentials(PasswordCredentials::class)
            }
            maven("https://repo.codemc.io/repository/maven-releases/") {
                name = "CodeMC"
                credentials(PasswordCredentials::class)
            }
            reposiliteMaven("https://maven.solo-studios.ca/releases/") {
                name = "SoloStudiosReleases"
                credentials(PasswordCredentials::class)
            }
            reposiliteMaven("https://maven.solo-studios.ca/snapshots/") {
                name = "SoloStudiosSnapshots"
                credentials(PasswordCredentials::class)
            }
        }
    }
}

repositories {
    mavenCentral()
    codeMC()
}

dependencies {
    api(libs.jetbrains.annotations)
    implementation(libs.slf4j.api)

    testImplementation(libs.bundles.junit)
}

tasks {
    withType<JavaCompile>().configureEach {
        options.isFork = true
        options.isIncremental = true
    }

    withType<Test>().configureEach {
        useJUnitPlatform()

        maxHeapSize = "2G"
        ignoreFailures = false
        failFast = true
        maxParallelForks = (Runtime.getRuntime().availableProcessors() - 1).coerceAtLeast(1)
    }
}