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
    implementation("io.ktor:ktor-server-core-jvm:2.3.1")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.1")
    implementation("io.ktor:ktor-server-status-pages-jvm:2.3.1")
    implementation("io.ktor:ktor-server-default-headers-jvm:2.3.1")

    // websocket
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.3.1")
    implementation("io.ktor:ktor-client-websockets:2.3.1")
    implementation("io.ktor:ktor-server-websockets:2.3.1")
}
