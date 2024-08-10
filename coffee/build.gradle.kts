plugins {
    kotlin("jvm")
}

group = "io.github.josebatista"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":di-lib"))
    api("javax.inject:javax.inject:1")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}