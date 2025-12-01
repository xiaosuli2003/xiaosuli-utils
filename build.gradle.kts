import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinxSerialization) apply false
    alias(libs.plugins.ktor) apply false
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

    group = "cn.xiaosuli.utils"
    version = "1.0.0"

    configure<KotlinJvmProjectExtension> {
        // 使用特定的 Java 版本，以便在不同的环境中更方便地进行开发。
        jvmToolchain(17)
        // 添加 kotlin 编译器参数
        compilerOptions {
            // 将注解同时应用到 value parameter（主构造函数的参数） 和 field（生成的私有字段） 上
            freeCompilerArgs.add("-Xannotation-default-target=param-property")
        }
    }

    // 启用 sources jar
    configure<JavaPluginExtension> {
        withSourcesJar()
        withJavadocJar()
    }

    configure<PublishingExtension> {
        publications {
            create<MavenPublication>("maven") {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()

                from(components["java"])
            }
        }
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
}