plugins {
    `java-library`

    id("me.champeau.jmh")
}

dependencies {
    testImplementation(libs.bundles.junit)
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()

        maxHeapSize = "2G"
        ignoreFailures = false
        failFast = true
        maxParallelForks = (Runtime.getRuntime().availableProcessors() - 1).coerceAtLeast(1)

        reports {
            html.required = false
            junitXml.required = false
        }
    }
}