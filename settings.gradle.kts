rootProject.name = "seismic"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// settings.gradle.kts
val isCiServer = System.getenv().containsKey("CI")
// Cache build artifacts, so expensive operations do not need to be re-computed
buildCache {
    local {
        isEnabled = !isCiServer
    }
}