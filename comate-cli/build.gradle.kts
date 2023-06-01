@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    application
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(projects.llmCore)

    implementation("org.archguard.scanner:analyser_sca:2.0.1")

    implementation(libs.kotlin.stdlib)
    implementation(libs.langtorch)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}

application {
    mainClass.set("org.archguard.copilot.MainKt")
}