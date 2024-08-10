plugins {
    id("com.google.devtools.ksp") version "2.0.10-1.0.24" apply false
    kotlin("jvm") version "2.0.10" apply false
}

rootProject.name = "OwnDependencyInjection"
include(":coffee")
include(":di-lib")
