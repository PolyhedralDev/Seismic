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
    implementation("org.jetbrains:annotations:24.1.0")
}
