@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    application
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(projects.llmCore)
    implementation(projects.comateCore)
    implementation(projects.architecture)

    implementation(libs.dotenv)
    implementation(libs.bundles.openai)
    implementation(libs.bundles.markdown)

    implementation(libs.kotlin.stdlib)
    implementation(libs.langtorch)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}

application {
    mainClass.set("org.archguard.copilot.MainKt")
}

tasks {
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "org.archguard.copilot.MainKt"))
        }
        // minimize()
        dependencies {
            exclude(dependency("org.junit.jupiter:.*:.*"))
            exclude(dependency("org.junit:.*:.*"))
            exclude(dependency("junit:.*:.*"))
        }
    }
}
