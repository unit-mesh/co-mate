val ktor_version = "2.3.1"

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    application
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
    id("io.ktor.plugin") version "2.3.1"
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    }
}

dependencies {
    implementation(projects.llmCore)

    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")
    implementation("io.ktor:ktor-server-default-headers:$ktor_version")

    // websocket
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-client-websockets:$ktor_version")
    implementation("io.ktor:ktor-server-websockets:$ktor_version")

    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation(libs.bundles.test)
}
