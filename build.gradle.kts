@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    id("jacoco-report-aggregation")
}

jacoco {
    toolVersion = "0.8.8"
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
    version = "0.1.0-SNAPSHOT"

//    java.sourceCompatibility = JavaVersion.VERSION_11
//    java.targetCompatibility = JavaVersion.VERSION_11

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

// !!!important for jacoco aggregation report only
dependencies {
//	jacocoAggregation(projects.factor)
}
