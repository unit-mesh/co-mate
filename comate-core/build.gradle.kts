@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(libs.kotlin.stdlib)

    implementation(projects.architecture)
    implementation(projects.llmCore)
    implementation(projects.metaAction)

    implementation(libs.bundles.openai)
    implementation(libs.bundles.markdown)

    implementation("org.archguard.scanner:analyser_sca:2.0.1")
    implementation("org.archguard.scanner:lang_kotlin:2.0.1")
    implementation("org.archguard.scanner:lang_java:2.0.1")
    implementation("org.archguard.scanner:feat_apicalls:2.0.1")

    implementation(libs.bundles.markdown)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}
