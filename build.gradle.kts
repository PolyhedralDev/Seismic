plugins {
    java
}

group = "com.dfsek"
version = "0.2.0"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.codemc.org/repository/maven-public") }

}

dependencies {
    implementation("org.jetbrains:annotations:26.0.1")
    implementation("org.slf4j:slf4j-api:2.0.16")
}