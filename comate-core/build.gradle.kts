@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
}

dependencies {
    implementation(libs.kotlin.stdlib)

    implementation(projects.architecture)
    implementation(projects.llmCore)
    implementation(projects.specLang)
    implementation(projects.specRuntime)

    implementation(libs.bundles.openai)
    implementation(libs.bundles.markdown)

    implementation(libs.archguard.analyser.sca)

    implementation(libs.archguard.lang.kotlin)
    implementation(libs.archguard.lang.java)
    implementation(libs.archguard.lang.typescript)
    implementation(libs.archguard.lang.golang)
    implementation(libs.archguard.lang.python)
    implementation(libs.archguard.feat.apicalls)
    implementation(libs.chapi.domain)


    // format results to CSV format
    implementation("app.softwork:kotlinx-serialization-csv:0.0.13")
    implementation("app.softwork:kotlinx-serialization-flf:0.0.13")


    compileOnly("org.archguard.codedb:action-toolkit:0.1.2")
    implementation("org.archguard.codedb:checkout:0.1.2")

    implementation(libs.kotlin.reflect)
    implementation(libs.reflections)

    implementation(libs.plantuml)

    implementation(libs.slf4j.simple)

    implementation(libs.bundles.markdown)
    implementation(projects.specRuntime)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.test.junit.engine)
}
