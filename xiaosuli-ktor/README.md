# xiaosuli-ktor

Ktor 框架的实用扩展工具库，提供便捷的配置读取和请求验证功能。

## 📦 安装

在您的 `build.gradle.kts` 中添加依赖：

```kotlin
dependencies {
    implementation("cn.xiaosuli.utils:xiaosuli-ktor:0.0.1-SNAPSHOT")
}
```

## 🚀 功能特性

### ApplicationConfig 扩展

提供安全的配置读取方法，避免空指针异常和类型转换错误。

#### 字符串配置读取

```kotlin
import cn.xiaosuli.utils.ktor.getStringOrDefault

val config = application.config
val databaseUrl = config.getStringOrDefault("database.url", "jdbc:h2:mem:test")
```

#### 数值配置读取

```kotlin
import cn.xiaosuli.utils.ktor.getIntOrDefault
import cn.xiaosuli.utils.ktor.getLongOrDefault

val port = config.getIntOrDefault("server.port", 8080)
val timeout = config.getLongOrDefault("request.timeout", 5000L)
```

### 请求验证工具

简化 Ktor 请求参数验证逻辑，提供类型安全的验证函数。

#### 非空验证

```kotlin
import cn.xiaosuli.utils.ktor.validateNotBlank

fun validateUserRequest(name: String, email: String): ValidationResult {
    return validateNotBlank(name, "用户名不能为空")
        .and(validateNotBlank(email, "邮箱不能为空"))
}
```

#### 长度验证

```kotlin
import cn.xiaosuli.utils.ktor.validateSize

fun validatePassword(password: String): ValidationResult {
    return validateSize(password, min = 8, max = 20, "密码长度必须在8-20位之间")
}
```

#### 组合验证

```kotlin
fun validateCreateUserRequest(
    username: String,
    email: String,
    password: String
): ValidationResult {
    return validateNotBlank(username, "用户名不能为空")
        .and(validateSize(username, min = 3, max = 20, "用户名长度必须在3-20位之间"))
        .and(validateNotBlank(email, "邮箱不能为空"))
        .and(validateSize(password, min = 8, max = 20, "密码长度必须在8-20位之间"))
}
```

## 📖 API 文档

### ApplicationConfig 扩展函数

#### `getStringOrDefault(key: String, defaultValue: String): String`

从配置中获取字符串值，如果不存在或为空则返回默认值。

**参数:**
- `key`: 配置键
- `defaultValue`: 默认值

**返回值:** 配置值或默认值

#### `getIntOrDefault(key: String, defaultValue: Int): Int`

从配置中获取整数值，如果不存在、为空或无法转换则返回默认值。

**参数:**
- `key`: 配置键
- `defaultValue`: 默认值

**返回值:** 配置值或默认值

#### `getLongOrDefault(key: String, defaultValue: Long): Long`

从配置中获取长整数值，如果不存在、为空或无法转换则返回默认值。

**参数:**
- `key`: 配置键
- `defaultValue`: 默认值

**返回值:** 配置值或默认值

### 验证函数

#### `validateNotBlank(value: String, message: String): ValidationResult`

验证字符串是否为空或空白字符。

**参数:**
- `value`: 要验证的字符串
- `message`: 验证失败时的错误消息

**返回值:** 验证结果

#### `validateSize(value: String, min: Int = 0, max: Int, message: String): ValidationResult`

验证字符串长度是否在指定范围内。

**参数:**
- `value`: 要验证的字符串
- `min`: 最小长度（默认0）
- `max`: 最大长度
- `message`: 验证失败时的错误消息

**返回值:** 验证结果

#### `ValidationResult.and(other: ValidationResult): ValidationResult`

合并两个验证结果，如果当前结果为无效则返回当前结果，否则返回另一个结果。

**参数:**
- `other`: 另一个验证结果

**返回值:** 合并后的验证结果

## 🔧 使用示例

### 完整的 Ktor 应用示例

```kotlin
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import cn.xiaosuli.utils.ktor.*

data class CreateUserRequest(
    val username: String,
    val email: String,
    val password: String
)

fun Application.module() {
    install(RequestValidation) {
        validate<CreateUserRequest> { request ->
            validateCreateUserRequest(request.username, request.email, request.password)
        }
    }
    
    routing {
        post("/users") {
            val request = call.receive<CreateUserRequest>()
            
            // 配置读取示例
            val maxUsers = application.config.getIntOrDefault("app.maxUsers", 1000)
            
            // 业务逻辑...
            call.respond("User created successfully")
        }
    }
}

private fun validateCreateUserRequest(
    username: String,
    email: String,
    password: String
): ValidationResult {
    return validateNotBlank(username, "用户名不能为空")
        .and(validateSize(username, min = 3, max = 20, "用户名长度必须在3-20位之间"))
        .and(validateNotBlank(email, "邮箱不能为空"))
        .and(validateSize(password, min = 8, max = 20, "密码长度必须在8-20位之间"))
}
```

## 📋 依赖要求

- **Kotlin**: 2.2.20+
- **Ktor**: 3.2.3+
- **JDK**: 21+

## 🔗 相关链接

- [主项目 README](../README.md)
- [Ktor 官方文档](https://ktor.io/)

## 📄 许可证

本项目采用 Apache 2.0 许可证 - 查看 [LICENSE](../LICENSE) 文件了解详情。