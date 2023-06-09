@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(projects.metaAction)
    implementation(projects.llmCore)

    implementation(libs.serialization.json)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)

    implementation(libs.slf4j.simple)

    implementation(libs.jupyter.api)
    implementation(libs.jupyter.kernel)
    implementation(libs.jupyter.shared.compiler)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}
