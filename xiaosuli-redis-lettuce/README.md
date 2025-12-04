# xiaosuli-redis-lettuce

基于 Lettuce 的 Redis 客户端类型安全扩展库，提供编译时类型检查和协程支持的 Redis 操作。

## 📦 安装

> **⚠️ 注意（重要）：** 本系列工具库目前尚未发布到 Maven 中央仓库，需要自己下载源码并编译到本地 Maven 仓库后使用。等待后续会上传到 Maven 中央仓库，但目前不是。

编译到本地 Maven 仓库：
```bash
../gradlew :xiaosuli-redis-lettuce:publishToMavenLocal
```

在您的 `build.gradle.kts` 中添加依赖：

```kotlin
dependencies {
    implementation("cn.xiaosuli.utils:xiaosuli-redis-lettuce:1.0.2")
}
```

## 🚀 功能特性

### 类型安全的 Redis Key

提供编译时类型检查的 Redis Key，避免类型错误和序列化问题。

#### 基础类型 Key

```kotlin
import cn.xiaosuli.utils.redis.lettuce.*

// 字符串类型 Key
val userTokenKey = stringRedisKey("user:token:123")

// 数值类型 Key
val userAgeKey = intRedisKey("user:age:123")
val userBalanceKey = doubleRedisKey("user:balance:123")

// 布尔类型 Key
val userActiveKey = booleanRedisKey("user:active:123")

// 集合类型 Key
val userTagsKey = stringSetRedisKey("user:tags:123")
```

#### 自定义对象 Key

```kotlin
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(val name: String, val email: String)

val userProfileKey = redisKey("user:profile:123", UserProfile.serializer())
```

### 协程扩展操作

提供支持 Kotlin 协程的异步 Redis 操作。

#### 基本操作

```kotlin
import io.lettuce.core.api.coroutines.RedisCoroutinesCommands
import cn.xiaosuli.utils.redis.lettuce.*

suspend fun example(redis: RedisCoroutinesCommands<String, String>) {
    // 设置值
    redis.set(userTokenKey, "abc123", expireSeconds = 3600)
    
    // 获取值
    val token: String? = redis.get(userTokenKey)
    
    // 删除 Key
    redis.del(userTokenKey, userAgeKey)
}
```

#### 自定义对象操作

```kotlin
@Serializable
data class UserSession(val userId: String, val loginTime: Long)

suspend fun manageUserSession(redis: RedisCoroutinesCommands<String, String>) {
    val sessionKey = redisKey("session:123", UserSession.serializer())
    
    // 存储自定义对象
    val session = UserSession("123", System.currentTimeMillis())
    redis.set(sessionKey, session, expireSeconds = 1800)
    
    // 读取自定义对象
    val storedSession: UserSession? = redis.get(sessionKey)
}
```

## 📖 API 文档

### Redis Key 创建函数

#### `stringRedisKey(name: String): RedisKey<String>`

创建字符串类型的 Redis Key。

**参数:**
- `name`: Key 名称

**返回值:** 字符串类型的 Redis Key

#### `intRedisKey(name: String): RedisKey<Int>`

创建整型类型的 Redis Key。

**参数:**
- `name`: Key 名称

**返回值:** 整型类型的 Redis Key

#### `longRedisKey(name: String): RedisKey<Long>`

创建长整型类型的 Redis Key。

**参数:**
- `name`: Key 名称

**返回值:** 长整型类型的 Redis Key

#### `doubleRedisKey(name: String): RedisKey<Double>`

创建双精度浮点型类型的 Redis Key。

**参数:**
- `name`: Key 名称

**返回值:** 双精度浮点型类型的 Redis Key

#### `booleanRedisKey(name: String): RedisKey<Boolean>`

创建布尔型类型的 Redis Key。

**参数:**
- `name`: Key 名称

**返回值:** 布尔型类型的 Redis Key

#### `stringSetRedisKey(name: String): RedisKey<Set<String>>`

创建字符串集合类型的 Redis Key。

**参数:**
- `name`: Key 名称

**返回值:** 字符串集合类型的 Redis Key

#### `redisKey(name: String, serializer: KSerializer<T>): RedisKey<T>`

创建自定义对象类型的 Redis Key。

**参数:**
- `name`: Key 名称
- `serializer`: 序列化器

**返回值:** 自定义对象类型的 Redis Key

### Redis 操作扩展函数

#### `get(key: RedisKey<T>): T?`

从 Redis 获取指定 Key 的值。

**参数:**
- `key`: Redis Key

**返回值:** 对应的值，如果 Key 不存在则返回 null

#### `set(key: RedisKey<T>, value: T, expireSeconds: Long = 0L)`

设置 Redis Key 的值。

**参数:**
- `key`: Redis Key
- `value`: 要设置的值
- `expireSeconds`: 过期时间（秒），0 表示永不过期

#### `del(vararg keys: RedisKey<*>)`

删除指定的 Redis Key。

**参数:**
- `keys`: 要删除的 Redis Key 列表

## 🔧 使用示例

### 完整的应用示例

```kotlin
import io.lettuce.core.RedisClient
import io.lettuce.core.api.coroutines.RedisCoroutinesCommands
import kotlinx.serialization.Serializable
import cn.xiaosuli.utils.redis.lettuce.*

@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String,
    val createdAt: Long
)

class UserRepository(private val redis: RedisCoroutinesCommands<String, String>) {
    
    private val userKey = { id: String -> redisKey("user:$id", User.serializer()) }
    private val userOnlineKey = { id: String -> booleanRedisKey("user:online:$id") }
    private val userSessionKey = { id: String -> stringRedisKey("user:session:$id") }
    
    suspend fun saveUser(user: User) {
        redis.set(userKey(user.id), user)
    }
    
    suspend fun getUser(id: String): User? {
        return redis.get(userKey(id))
    }
    
    suspend fun setUserOnline(id: String, isOnline: Boolean) {
        redis.set(userOnlineKey(id), isOnline)
    }
    
    suspend fun setUserSession(id: String, sessionToken: String, expireSeconds: Long = 3600) {
        redis.set(userSessionKey(id), sessionToken, expireSeconds)
    }
    
    suspend fun deleteUser(id: String) {
        redis.del(
            userKey(id),
            userOnlineKey(id),
            userSessionKey(id)
        )
    }
}

// 使用示例
suspend fun main() {
    val redisClient = RedisClient.create("redis://localhost:6379")
    val connection = redisClient.connect().coroutines()
    
    val userRepository = UserRepository(connection)
    
    val user = User("123", "张三", "zhangsan@example.com", System.currentTimeMillis())
    
    // 保存用户
    userRepository.saveUser(user)
    
    // 设置用户在线状态
    userRepository.setUserOnline("123", true)
    
    // 设置用户会话
    userRepository.setUserSession("123", "session_token_abc", 1800)
    
    // 查询用户
    val retrievedUser = userRepository.getUser("123")
    println("Retrieved user: $retrievedUser")
    
    connection.close()
    redisClient.shutdown()
}
```

## 🔌 配置说明

### 序列化配置

库使用默认的 JSON 序列化配置，设置不序列化值为 null 的属性：

```kotlin
val DefaultJson = Json {
    // 设置不序列化值为 null 的属性
    explicitNulls = false
}
```

### 依赖要求

- **Kotlin**: 2.2.20+
- **Kotlinx Serialization**: 1.9.0+
- **Lettuce Core**: 6.3.2+
- **JDK**: 21+

## 🔗 相关链接

- [主项目 README](../README.md)
- [Lettuce 官方文档](https://lettuce.io/)
- [Kotlinx Serialization 文档](https://github.com/Kotlin/kotlinx.serialization)

## 📄 许可证

本项目采用 Apache 2.0 许可证 - 查看 [LICENSE](../LICENSE) 文件了解详情。