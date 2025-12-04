# xiaosuli-utils

由[xiaosuli](https://github.com/xiaosuli2003)出品的一个现代化的 Kotlin 工具库集合，提供实用的扩展函数和工具类，帮助开发者更高效地构建 Kotlin 应用程序。

## 📦 模块概览

| 模块 | 版本 | 描述 |
|------|------|------|
| [xiaosuli-ktor](./xiaosuli-ktor/README.md) | 1.0.2 | Ktor 框架的实用扩展工具 |
| [xiaosuli-redis-lettuce](./xiaosuli-redis-lettuce/README.md) | 1.0.2 | Lettuce Redis 客户端的类型安全扩展 |
| [xiaosuli-exposed-postgresql](./xiaosuli-exposed-postgresql/README.md) | 1.0.2 | Exposed ORM 的 PostgreSQL 类型扩展 |
| [xiaosuli-kotlin-serialization](./xiaosuli-kotlin-serialization/README.md) | 1.0.2 | Kotlin Serialization 的实用序列化器扩展 |

## 🚀 快速开始

### 环境要求

- **JDK**: 21+
- **Kotlin**: 2.2.20+
- **Gradle**: 8.14+

### 构建项目

```bash
# 克隆项目
git clone https://github.com/xiaosuli/xiaosuli-utils.git
cd xiaosuli-utils

# 构建所有模块
./gradlew build

# 运行测试
./gradlew test
```

### 使用特定模块

> **⚠️ 注意（重要）：** 本系列工具库目前尚未发布到 Maven 中央仓库，需要自己下载源码并编译到本地 Maven 仓库后使用。等待后续会上传到 Maven 中央仓库，但目前不是。

编译到本地 Maven 仓库：

1. **发布所有模块**：
```bash
./gradlew publishToMavenLocal
```

2. **发布特定模块**：
```bash
# 例如仅发布 xiaosuli-ktor 模块
./gradlew :xiaosuli-ktor:publishToMavenLocal

# 例如仅发布 xiaosuli-redis-lettuce 模块
./gradlew :xiaosuli-redis-lettuce:publishToMavenLocal

# 例如仅发布 xiaosuli-exposed-postgresql 模块
./gradlew :xiaosuli-exposed-postgresql:publishToMavenLocal

# 例如仅发布 xiaosuli-kotlin-serialization 模块
./gradlew :xiaosuli-kotlin-serialization:publishToMavenLocal
```

在您的 `build.gradle.kts` 中添加依赖：

```kotlin
dependencies {
    // Ktor 扩展模块
    implementation("cn.xiaosuli.utils:xiaosuli-ktor:1.0.2")
    
    // Redis Lettuce 扩展模块
    implementation("cn.xiaosuli.utils:xiaosuli-redis-lettuce:1.0.2")
    
    // Exposed PostgreSQL 扩展模块
    implementation("cn.xiaosuli.utils:xiaosuli-exposed-postgresql:1.0.2")
    
    // Kotlin Serialization 扩展模块
    implementation("cn.xiaosuli.utils:xiaosuli-kotlin-serialization:1.0.2")
}
```

## 📚 模块详情

### xiaosuli-ktor

提供 Ktor 框架的实用扩展：

- **ApplicationConfig 扩展**: 安全的配置读取方法
- **请求验证工具**: 简化参数验证逻辑

### xiaosuli-redis-lettuce

提供类型安全的 Redis 操作：

- **类型安全 Key**: 编译时类型检查的 Redis Key
- **协程扩展**: 支持 Kotlin 协程的异步操作
- **JSON 序列化**: 基于 Kotlinx Serialization 的对象序列化

### xiaosuli-exposed-postgresql

提供 Exposed ORM 框架的 PostgreSQL 类型扩展：

- **INET 类型支持**: 为 PostgreSQL 的 INET 网络地址类型提供扩展支持
- **类型安全**: 编译时类型检查的列定义

### xiaosuli-kotlin-serialization

提供 Kotlinx Serialization 的实用序列化器：

- **LocalDateTime 序列化**: 将 Kotlin LocalDateTime 序列化为标准格式字符串
- **类型安全**: 编译时类型检查的序列化支持

## 🛠️ 开发指南

### 项目结构

```
xiaosuli-utils/
├── xiaosuli-ktor/              # Ktor 扩展模块
│   ├── src/main/kotlin/
│   └── build.gradle.kts
├── xiaosuli-redis-lettuce/     # Redis 扩展模块
│   ├── src/main/kotlin/
│   └── build.gradle.kts
├── xiaosuli-exposed-postgresql/ # PostgreSQL 扩展模块
│   ├── src/main/kotlin/
│   └── build.gradle.kts
├── xiaosuli-kotlin-serialization/ # 序列化扩展模块
│   ├── src/main/kotlin/
│   └── build.gradle.kts
├── build.gradle.kts            # 根项目构建配置
├── settings.gradle.kts         # 项目设置
└── README.md                  # 项目说明
```

### 添加新模块

1. 在 `settings.gradle.kts` 中添加新模块：
   ```kotlin
   include(":new-module-name")
   ```

2. 创建模块目录和构建文件
3. 实现模块功能
4. 更新主 README 文档

## 🤝 贡献指南

我们欢迎各种形式的贡献！请参考以下步骤：

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/amazing-feature`)
3. 提交更改 (`git commit -m 'Add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 创建 Pull Request

### 代码规范

- 使用 Kotlin 官方编码规范
- 为公共 API 添加完整的 KDoc 文档
- 编写单元测试覆盖核心功能
- 保持向后兼容性

## 📄 许可证

本项目采用 Apache 2.0 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 📞 联系方式

- 项目主页: https://github.com/xiaosuli/xiaosuli-utils
- 问题反馈: https://github.com/xiaosuli/xiaosuli-utils/issues

## 🙏 致谢

感谢所有为这个项目做出贡献的开发者！

---

⭐ 如果这个项目对您有帮助，请给我们一个 star！