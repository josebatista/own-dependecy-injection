val kspVersion: String by project

plugins {
    kotlin("jvm")
}

group = "io.github.josebatista"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:${kspVersion}")
    implementation(project(":di-lib"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}