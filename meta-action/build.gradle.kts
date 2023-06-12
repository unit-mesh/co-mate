@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(projects.architecture)
    implementation(libs.kotlin.stdlib)
    implementation(libs.serialization.json)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}
