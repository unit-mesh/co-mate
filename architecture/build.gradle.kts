@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(libs.chapi.domain)

    implementation(libs.kotlin.reflect)
    implementation(libs.serialization.json)

    testImplementation(libs.bundles.test)
}
