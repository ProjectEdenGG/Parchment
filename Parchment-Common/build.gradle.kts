import java.util.Locale
import java.net.URI

plugins {
    `maven-publish`
}

java {
    withSourcesJar()
    withJavadocJar()
}

dependencies {
    val annotations = "org.jetbrains:annotations:21.0.1"
    compileOnly(annotations)
    testCompileOnly(annotations)
}

configure<PublishingExtension> {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
        groupId = project.group as String?
        artifactId = "Parchment-Common"
        version = "1.17-R0.1-SNAPSHOT"

        pom {
            name.set(project.name)
            description.set("Common annotations for Parchment and other software")
        }
    }

    repositories {
        maven {
            credentials {
                username = properties["edenusr"] as String?
                password = properties["edenpwd"] as String?
            }
            url = URI.create("https://sonatype.projecteden.gg/repository/maven-snapshots/")
        }
    }
}