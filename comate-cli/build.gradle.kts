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

    implementation("org.jetbrains.kotlinx:kotlin-deeplearning-api:0.5.2")
    implementation("org.jetbrains.kotlinx:kotlin-deeplearning-onnx:0.5.2")

    implementation("ai.djl.huggingface:tokenizers:0.22.1")
//    implementation("org.nd4j:nd4j-backends:1.0.0-M2.1")
//    implementation("org.nd4j:nd4j-native:1.0.0-M2.1")
//    implementation("org.nd4j:nd4j-api:1.0.0-M2.1")

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
