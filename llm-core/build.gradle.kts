@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(projects.metaAction)
    implementation(libs.kotlin.stdlib)

    implementation(libs.bundles.openai)

    implementation("com.microsoft.onnxruntime:onnxruntime:1.15.0")

    implementation("ai.djl.huggingface:tokenizers:0.22.1")

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}
