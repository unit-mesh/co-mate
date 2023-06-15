@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(projects.specLang)
    implementation(projects.llmCore)

    implementation(libs.serialization.json)
    implementation(libs.kotlin.stdlib)
    implementation(libs.bundles.markdown)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}
