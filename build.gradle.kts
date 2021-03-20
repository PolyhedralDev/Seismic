plugins {
    java
}

group = "com.dfsek"
version = "0.1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.codemc.org/repository/maven-public") }

}

dependencies {
    testImplementation("junit", "junit", "4.12")


    implementation("com.dfsek:Paralithic:0.3.2")
    implementation("net.jafama:jafama:2.3.2")
    implementation("org.ow2.asm:asm:9.0")
}
