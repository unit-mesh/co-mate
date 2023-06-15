@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(projects.architecture)
    implementation(libs.kotlin.stdlib)
    implementation(libs.serialization.json)

    // for backend code model
    implementation(libs.chapi.domain)

    // we use kotest DSL as assertion for Architecture
//    implementation("io.kotest:kotest-assertions-api:5.5.4")
//    implementation("io.kotest:kotest-assertions-shared-jvm:5.5.4")

    testImplementation(libs.bundles.test)

    testRuntimeOnly(libs.test.junit.engine)
}
