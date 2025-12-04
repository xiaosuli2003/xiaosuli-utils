# xiaosuli-kotlin-serialization

Kotlin Serialization 的实用序列化器扩展库，提供常见类型的自定义序列化支持。

## 📦 安装

> **⚠️ 注意（重要）：** 本系列工具库目前尚未发布到 Maven 中央仓库，需要自己下载源码并编译到本地 Maven 仓库后使用。等待后续会上传到 Maven 中央仓库，但目前不是。

编译到本地 Maven 仓库：
```bash
../gradlew :xiaosuli-kotlin-serialization:publishToMavenLocal
```

在您的 `build.gradle.kts` 中添加依赖：

```kotlin
dependencies {
    implementation("cn.xiaosuli.utils:xiaosuli-kotlin-serialization:1.0.2")
}
```

## 🚀 功能特性

### LocalDateTime 字符串序列化

将 Kotlinx LocalDateTime 类型序列化为标准格式的字符串（yyyy-MM-dd HH:mm:ss）。

#### 使用示例

```kotlin
import cn.xiaosuli.utils.kotlin.serialization.serializer.LocalDateTimeAsStringSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Event(
    val id: String,
    val name: String,
    @Serializable(with = LocalDateTimeAsStringSerializer::class)
    val timestamp: LocalDateTime
)

// 创建事件
val now = LocalDateTime(2024, 1, 1, 12, 0, 0)
val event = Event("123", "New Year Event", now)

// 序列化为 JSON
val json = Json.encodeToString(event)
println(json) // 输出: {"id":"123","name":"New Year Event","timestamp":"2024-01-01 12:00:00"}

// 反序列化
val deserializedEvent = Json.decodeFromString<Event>(json)
println(deserializedEvent.timestamp) // 输出: 2024-01-01T12:00
```

#### 在数据类中使用

```kotlin
import cn.xiaosuli.utils.kotlin.serialization.serializer.LocalDateTimeAsStringSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class UserActivity(
    val userId: String,
    val action: String,
    @Serializable(with = LocalDateTimeAsStringSerializer::class)
    val createdAt: LocalDateTime,
    @Serializable(with = LocalDateTimeAsStringSerializer::class)
    val updatedAt: LocalDateTime
)
```

## 📖 API 文档

### 序列化器

#### `LocalDateTimeAsStringSerializer`

将 Kotlinx LocalDateTime 序列化为标准格式字符串（yyyy-MM-dd HH:mm:ss）。

**特点：**
- 使用标准格式字符串进行序列化，易于阅读和存储
- 支持双向转换：LocalDateTime ↔ String
- 基于 DateTimeFormatter 实现，确保格式一致性

**序列化格式：** yyyy-MM-dd HH:mm:ss

## 📋 依赖要求

- **Kotlin**: 2.2.20+
- **Kotlinx Serialization**: 1.9.0+
- **Kotlinx DateTime**: 0.6.0+
- **JDK**: 21+

## 🔗 相关链接

- [主项目 README](../README.md)
- [Kotlinx Serialization 官方文档](https://github.com/Kotlin/kotlinx.serialization)
- [Kotlinx DateTime 官方文档](https://github.com/Kotlin/kotlinx-datetime)

## 📄 许可证

本项目采用 Apache 2.0 许可证 - 查看 [LICENSE](../LICENSE) 文件了解详情。