plugins {
    // alias(libs.plugins.ktor)
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.request.validation)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.schema.kenerator.swagger)
    testImplementation(libs.kotlin.test)
}
