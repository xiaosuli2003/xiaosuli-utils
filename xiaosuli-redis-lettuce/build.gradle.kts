plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinxSerialization)
}

group = "cn.xiaosuli.utils"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.lettuce.core)
}

kotlin {
    // 使用特定的 Java 版本，以便在不同的环境中更方便地进行开发。
    jvmToolchain(21)
}

tasks.withType<Test>().configureEach {
    // 将所有测试的 Gradle 任务配置为使用 JUnitPlatform。
    useJUnitPlatform()

    // 记录所有测试结果的信息，不仅包括失败的测试结果，还包括成功的测试结果。
    /*testLogging {
        events(
            TestLogEvent.FAILED,
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED
        )
    }*/
}