plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")

    implementation("com.google.guava:guava:31.1-jre")
}

application {
    mainClass.set("demo.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

