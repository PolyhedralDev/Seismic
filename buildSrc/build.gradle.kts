plugins {
    `kotlin-dsl`
    kotlin("jvm") version embeddedKotlinVersion
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(gradlePlugin(libs.plugins.nyx, libs.versions.nyx))
    implementation(gradlePlugin(libs.plugins.jmh, libs.versions.jmh))

    // https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

fun gradlePlugin(id: Provider<PluginDependency>, version: Provider<String>): String {
    val pluginId = id.get().pluginId
    return "$pluginId:$pluginId.gradle.plugin:${version.get()}"
}