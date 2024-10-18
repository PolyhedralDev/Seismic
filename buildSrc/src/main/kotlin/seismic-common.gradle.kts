import ca.solostudios.nyx.util.codeMC

plugins {
    `java-library`

    id("ca.solo-studios.nyx")
}

nyx {
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

    info {
        name = "Seismic"
        group = "com.dfsek"
        module = rootProject.name
        version = rootProject.version.toString()
        description = """
            Seismic is A Java sampler, math, and type library.
        """.trimIndent()

        organizationName = "Polyhedral Development"
        organizationUrl = "https://github.com/PolyhedralDev/"

        repository.fromGithub("PolyhedralDev", "Seismic")

        developer {
            id = "duplexsys"
            name = "Zoë Gidiere"
            email = "duplexsys@protonmail.com"
            url = "https://github.com/duplexsystem"
        }
    }
}

repositories {
    mavenCentral()
    codeMC()
}

dependencies {
    implementation(libs.jetbrains.annotations)
    implementation(libs.slf4j.api)
}

tasks {
    withType<JavaCompile>().configureEach {
        options.isFork = true
        options.isIncremental = true
    }
}