@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(libs.kotlin.stdlib)

    implementation(projects.architecture)

    implementation("org.archguard.scanner:analyser_sca:2.0.1")

    implementation("org.jetbrains.kotlinx:kotlin-deeplearning-api:0.5.2")
    implementation("org.jetbrains.kotlinx:kotlin-deeplearning-onnx:0.5.2")

    implementation("ai.djl.huggingface:tokenizers:0.22.1")
//    implementation("org.nd4j:nd4j-backends:1.0.0-M2.1")
//    implementation("org.nd4j:nd4j-native:1.0.0-M2.1")
//    implementation("org.nd4j:nd4j-api:1.0.0-M2.1")


    implementation(libs.bundles.markdown)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}
