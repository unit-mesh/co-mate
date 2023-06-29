@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(projects.specLang)
    implementation(libs.kotlin.stdlib)
    implementation(libs.coroutines.core)

    implementation(libs.bundles.openai)
    implementation(libs.jtokkit)

    implementation("ch.qos.logback:logback-classic:1.4.5")

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}
