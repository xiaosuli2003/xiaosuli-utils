dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}

plugins {
    // 使用 Foojay 工具链插件自动下载子项目所需的 JDK。
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}


include(":xiaosuli-ktor")
include(":xiaosuli-redis-lettuce")

rootProject.name = "xiaosuli-utils"