import org.jetbrains.compose.desktop.application.dsl.TargetFormat

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    kotlin("kapt")
    alias(libs.plugins.serialization)
    id("org.jetbrains.compose")
}

group = "org.archguard.comate"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(libs.serialization.json)

    implementation(libs.dotenv)
    implementation(libs.bundles.openai)
    implementation(libs.bundles.markdown)

    implementation("androidx.compose.material3:material3:1.1.0-alpha06")

    implementation("io.reactivex.rxjava3:rxjava:3.1.6")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.datastore:datastore-preferences-rxjava3:1.0.0")
    implementation("androidx.datastore:datastore-preferences-core:1.0.0")


    implementation(libs.gson)

    // Image loading
    // implementation("io.coil-kt:coil-compose:2.3.0")

    // Dependency Injection
    implementation("org.kodein.di:kodein-di:7.20.1")
    runtimeOnly("org.kodein.di:kodein-di-framework-compose:7.20.1")

    // UI/UX Utils
    val richtextVersion = "0.16.0"
    implementation("com.halilibo.compose-richtext:richtext-commonmark:${richtextVersion}")
    implementation("com.halilibo.compose-richtext:richtext-ui-material:${richtextVersion}")
    implementation("com.halilibo.compose-richtext:richtext-ui-material3:${richtextVersion}")

    val voyagerVersion = "1.0.0-rc05"
    implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-kodein:$voyagerVersion")

    // ONNX ML
//    implementation("io.kinference:inference-api:0.2.13")
//    implementation("io.kinference:ndarray-api:0.2.13")
    implementation("io.kinference:inference-core:0.2.13")
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    // kinference
    maven {
        url = uri("https://packages.jetbrains.team/maven/p/ki/maven")
    }
}

kotlin {
    jvm {
        jvmToolchain(17)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "comate-gui"
            packageVersion = "1.0.0"
        }
    }
}


kapt {
    correctErrorTypes = true
}
