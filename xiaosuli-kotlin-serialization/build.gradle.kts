plugins {
    alias(libs.plugins.kotlinxSerialization)
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    testImplementation(libs.kotlin.test)
}