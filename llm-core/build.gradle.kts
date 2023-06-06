@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(libs.kotlin.stdlib)

    implementation(libs.bundles.openai)

    implementation("org.jetbrains.kotlinx:kotlin-deeplearning-api:0.5.2")
    implementation("org.jetbrains.kotlinx:kotlin-deeplearning-onnx:0.5.2")

    implementation("ai.djl.huggingface:tokenizers:0.22.1")

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}
