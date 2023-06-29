@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    id("jacoco-report-aggregation")
    id("com.github.nbaztec.coveralls-jacoco") version "1.2.15"
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
    version = "0.1.9"

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
