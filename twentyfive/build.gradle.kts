import net.ltgt.gradle.errorprone.errorprone

plugins {
    `java-library`
    alias(libs.plugins.errorprone)
}

group = "chaoticgoose.uk"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(libs.funcj.parser)
    implementation(libs.gatherers4j)
    implementation(libs.jresult)

    implementation(libs.jspecify)
    errorprone(libs.nullaway)
    errorprone(libs.errorprone.core)

    testImplementation(libs.archunit)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)

    testImplementation(libs.assertj)
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }

    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

tasks.withType<JavaCompile>().configureEach {
    options.errorprone {
        disableAllChecks = true // Other errorprone checks are disabled
        option("NullAway:OnlyNullMarked", "true") // Enable nullness checks only in null-marked code
        error("NullAway") // bump checks from warnings (default) to errors
    }
    if (name.lowercase().contains("test")) {
        options.errorprone {
            disable("NullAway")
        }
    }
}
