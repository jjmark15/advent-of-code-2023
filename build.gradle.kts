plugins {
    kotlin("jvm") version "1.9.20"
}

group = "clumsywizard"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.kittinunf.result:result:5.3.0")
    implementation(platform("dev.forkhandles:forkhandles-bom:2.3.0.0"))
    implementation("com.github.h0tk3y.betterParse:better-parse:0.4.4")
    testImplementation(kotlin("test"))
    testImplementation("io.strikt:strikt-core:0.34.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}