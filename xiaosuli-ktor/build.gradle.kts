plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
}

group = "cn.xiaosuli.utils"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(libs.ktor.server.request.validation)
    implementation(libs.ktor.server.core)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}