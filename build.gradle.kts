plugins {
    java
    `maven-publish`

    alias(libs.plugins.axion.release)

    `seismic-common`
    `seismic-testing`
    `seismic-publishing`
}

version = scmVersion.version