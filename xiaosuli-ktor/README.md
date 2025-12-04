# xiaosuli-ktor

Ktor 框架的实用扩展工具库，提供便捷的配置读取、请求验证、统一响应格式、HTTP状态码定义和请求参数处理功能。

## 📦 安装

> **⚠️ 注意（重要）：** 本系列工具库目前尚未发布到 Maven 中央仓库，需要自己下载源码并编译到本地 Maven 仓库后使用。等待后续会上传到 Maven 中央仓库，但目前不是。

编译到本地 Maven 仓库：
```bash
../gradlew :xiaosuli-ktor:publishToMavenLocal
```

在您的 `build.gradle.kts` 中添加依赖：

```kotlin
dependencies {
    implementation("cn.xiaosuli.utils:xiaosuli-ktor:1.0.2")
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

### 统一响应格式

提供标准化的API响应结构和辅助函数，简化响应处理。

```kotlin
import cn.xiaosuli.utils.ktor.*

// 成功响应
suspend fun getUser(call: ApplicationCall) {
    val user = userService.findById(call.parameters["id"]!!)
    call.respondOk(user)
}

// 失败响应
suspend fun deleteUser(call: ApplicationCall) {
    if (!userService.exists(call.parameters["id"]!!)) {
        call.respondFail(HttpCode.NotFound, "用户不存在")
        return
    }
    userService.delete(call.parameters["id"]!!)
    call.respondOk(message = "用户删除成功")
}
```

### 请求参数处理

提供将查询参数转换为指定类型的功能，简化参数获取。

```kotlin
import cn.xiaosuli.utils.ktor.requestQueryParameters

data class SearchParams(val keyword: String, val page: Int, val size: Int)

suspend fun search(call: ApplicationCall) {
    val params = call.requestQueryParameters<SearchParams>()
    val results = searchService.search(params.keyword, params.page, params.size)
    call.respondOk(results)
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

### 统一响应

#### `HttpResponse` 类

```kotlin
@Serializable
class HttpResponse<T>(
    val code: Int,
    val msg: String,
    val data: T? = null,
    val timestamp: Long
)
```

统一响应结果类，包含状态码、提示信息、数据和时间戳。

**属性:**
- `code`: HTTP状态码
- `msg`: 响应消息
- `data`: 响应数据
- `timestamp`: 时间戳

#### `R.ok`

创建成功响应结果。

```kotlin
fun <T> ok(
    data: T? = null,
    httpStatus: HttpCode = HttpCode.Success,
    message: String? = null
): HttpResponse<T>
```

**参数:**
- `data`: 响应数据，可选
- `httpStatus`: HTTP状态码，默认为成功
- `message`: 响应消息，可选

**返回值:** HttpResponse实例

#### `R.fail`

创建失败响应结果。

```kotlin
fun fail(
    httpStatus: HttpCode,
    message: String? = null
): HttpResponse<Unit>
```

**参数:**
- `httpStatus`: HTTP状态码
- `message`: 响应消息，可选

**返回值:** HttpResponse实例

#### `ApplicationCall.respondOk`

直接发送成功响应。

```kotlin
suspend inline fun <reified T> ApplicationCall.respondOk(
    data: T? = null,
    httpStatus: HttpCode = HttpCode.Success,
    message: String? = null
)
```

**参数:**
- `data`: 响应数据，可选
- `httpStatus`: HTTP状态码，默认为成功
- `message`: 响应消息，可选

#### `ApplicationCall.respondFail`

直接发送失败响应。

```kotlin
suspend fun ApplicationCall.respondFail(
    httpStatus: HttpCode,
    message: String? = null
)
```

**参数:**
- `httpStatus`: HTTP状态码
- `message`: 响应消息，可选

### 请求参数处理

#### `ApplicationCall.requestQueryParameters`

将请求中的查询参数转换为指定类型。

```kotlin
inline fun <reified T> ApplicationCall.requestQueryParameters(): T
```

**类型参数:**
- `T`: 目标类型

**返回值:** 转换后的对象

**异常:**
- 如果参数缺少或格式错误，抛出BadRequestException

### HTTP状态码枚举

#### `HttpCode`

预定义的HTTP状态码枚举。

```kotlin
enum class HttpCode(val value: Int, val reasonPhrase: String)
```

**包含的状态码:**
- `Success(2233, "操作成功")`: 自定义成功状态码
- `BadRequest(400, "参数列表错误（缺少，格式不匹配）")`: 请求参数错误
- `Unauthorized(401, "未授权")`: 未授权访问
- `Forbidden(403, "访问受限，授权过期")`: 权限不足
- `NotFound(404, "资源，服务未找到")`: 资源不存在
- `MethodNotAllowed(405, "不允许的HTTP方法")`: HTTP方法不允许
- `Error(500, "系统内部错误")`: 服务器内部错误

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

data class UserResponse(
    val id: String,
    val username: String,
    val email: String
)

data class SearchParams(
    val keyword: String,
    val page: Int = 1,
    val size: Int = 10
)

fun Application.module() {
    install(RequestValidation) {
        validate<CreateUserRequest> { request ->
            validateCreateUserRequest(request.username, request.email, request.password)
        }
    }
    
    routing {
        // 创建用户
        post("/users") {
            val request = call.receive<CreateUserRequest>()
            
            // 配置读取示例
            val maxUsers = application.config.getIntOrDefault("app.maxUsers", 1000)
            
            // 模拟业务逻辑
            val user = UserResponse("1", request.username, request.email)
            
            // 使用统一响应格式
            call.respondOk(user, message = "用户创建成功")
        }
        
        // 获取用户
        get("/users/{id}") {
            val id = call.parameters["id"] ?: return@get call.respondFail(HttpCode.BadRequest, "用户ID不能为空")
            
            // 模拟查询用户
            if (id == "1") {
                val user = UserResponse("1", "testuser", "test@example.com")
                call.respondOk(user)
            } else {
                call.respondFail(HttpCode.NotFound, "用户不存在")
            }
        }
        
        // 搜索用户
        get("/users") {
            try {
                // 将查询参数转换为对象
                val params = call.requestQueryParameters<SearchParams>()
                
                // 模拟搜索结果
                val users = listOf(
                    UserResponse("1", "testuser", "test@example.com")
                )
                
                call.respondOk(users)
            } catch (e: Exception) {
                call.respondFail(HttpCode.BadRequest, "参数错误：${e.message}")
            }
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
- **Ktor**: 3.0.0+
- **JDK**: 21+

## 🔗 相关链接

- [主项目 README](../README.md)
- [Ktor 官方文档](https://ktor.io/)

## 📄 许可证

本项目采用 Apache 2.0 许可证 - 查看 [LICENSE](../LICENSE) 文件了解详情。