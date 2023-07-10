@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    id("jacoco-report-aggregation")
    id("com.github.nbaztec.coveralls-jacoco") version "1.2.15"

    id("java-library")
    id("maven-publish")
    publishing
    signing
}

jacoco {
    toolVersion = "0.8.8"
}

kotlin {
    jvmToolchain(11)
}

repositories {
    mavenCentral()
    mavenLocal()
    google()
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "jacoco")

    repositories {
        mavenCentral()
        mavenLocal()
    }

    group = "org.archguard.comate"
    version = "0.2.0"

    java.sourceCompatibility = JavaVersion.VERSION_11
    java.targetCompatibility = JavaVersion.VERSION_11

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    tasks.test {
        finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
    }

    tasks.jacocoTestReport {
        dependsOn(tasks.test) // tests are required to run before generating the report
    }

    tasks.jacocoTestReport {
        reports {
            xml.required.set(true)
            csv.required.set(false)
            html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
        }
    }
}

configure(
    allprojects
            - project(":comate-server")
            - project(":comate-cli")
) {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")
    apply(plugin = "publishing")

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
                versionMapping {
                    usage("java-api") {
                        fromResolutionOf("runtimeClasspath")
                    }
                    usage("java-runtime") {
                        fromResolutionResult()
                    }
                }
                pom {
                    name.set("ArchGuard")
                    description.set(" ArchGuard is a architecture governance tool which can analysis architecture in container, component, code level, create architecture fitness functions, and anaysis system dependencies.. ")
                    url.set("https://archguard.org/")
                    licenses {
                        license {
                            name.set("MIP-2.0")
                            url.set("https://raw.githubusercontent.com/archguard/co-mate/master/LICENSE")
                        }
                    }
                    developers {
                        developer {
                            id.set("ArchGuard")
                            name.set("ArchGuard Team")
                            email.set("h@phodal.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com/archguard/co-mate.git")
                        developerConnection.set("scm:git:ssh://github.com/archguard/co-mate.git")
                        url.set("https://github.com/archguard/co-mate/")
                    }
                }
            }
        }

        repositories {
            maven {
                val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

                credentials {
                    username =
                        (if (project.findProperty("sonatypeUsername") != null) project.findProperty("sonatypeUsername")
                        else System.getenv("MAVEN_USERNAME")).toString()
                    password =
                        (if (project.findProperty("sonatypePassword") != null) project.findProperty("sonatypePassword")
                        else System.getenv("MAVEN_PASSWORD")).toString()
                }
            }
        }
    }

    signing {
        sign(publishing.publications["mavenJava"])
    }

    java {
        withJavadocJar()
        withSourcesJar()
    }
}


dependencies {
    jacocoAggregation(project(":architecture"))

    jacocoAggregation(project(":comate-server"))
    jacocoAggregation(project(":comate-core"))

    jacocoAggregation(project(":llm-core"))

    jacocoAggregation(project(":spec-lang"))
    jacocoAggregation(project(":spec-partitioner"))
    jacocoAggregation(project(":spec-runtime"))
}

reporting {
    reports {
        val jacocoRootReport by creating(JacocoCoverageReport::class) {
            testType.set(TestSuiteType.UNIT_TEST)
        }
    }
}

tasks.check {
    dependsOn(tasks.named<JacocoReport>("jacocoRootReport"))
}
