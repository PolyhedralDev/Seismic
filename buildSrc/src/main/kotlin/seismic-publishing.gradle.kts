plugins {
    java
    `maven-publish`

    id("ca.solo-studios.nyx")
}

nyx {
    publishing {
        withSignedPublishing()

        repositories {
//            maven {
//                name = "Sonatype"
//
//                val repositoryId: String? by project
//                url = when {
//                    repositoryId != null -> uri("https://s01.oss.sonatype.org/service/local/staging/deployByRepositoryId/$repositoryId/")
//                    else -> uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
//                }
//
//                credentials(PasswordCredentials::class)
//            }
            maven("https://repo.codemc.io/repository/maven-releases/") {
                name = "CodeMC"
                credentials(PasswordCredentials::class)
            }
            maven("https://maven.solo-studios.ca/releases/") {
                credentials(PasswordCredentials::class)
                authentication { // publishing doesn't work without this for some reason
                    create<BasicAuthentication>("basic")
                }
            }
        }
    }
}