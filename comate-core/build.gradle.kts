@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(libs.kotlin.stdlib)

    implementation(projects.architecture)

    implementation("org.archguard.scanner:analyser_sca:2.0.1")

    implementation(libs.bundles.markdown)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}
