plugins {
    kotlin("jvm") version "2.2.20"
    `java-library`
}

group = "clumsywizard"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.kittinunf.result:result:5.6.0")
    implementation(platform("dev.forkhandles:forkhandles-bom:2.22.5.0"))
    implementation("dev.forkhandles:parser4k")
    implementation("com.github.h0tk3y.betterParse:better-parse:0.4.4")
    testImplementation(kotlin("test"))
    testImplementation("io.strikt:strikt-core:0.35.1")
    testImplementation("com.willowtreeapps.assertk:assertk:0.28.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(24)
}