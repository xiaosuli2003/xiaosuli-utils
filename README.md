# xiaosuli-utils

一个现代化的 Kotlin 工具库集合，提供实用的扩展函数和工具类，帮助开发者更高效地构建 Kotlin 应用程序。

## 📦 模块概览

| 模块 | 版本 | 描述 |
|------|------|------|
| [xiaosuli-ktor](./xiaosuli-ktor/README.md) | 0.0.1-SNAPSHOT | Ktor 框架的实用扩展工具 |
| [xiaosuli-redis-lettuce](./xiaosuli-redis-lettuce/README.md) | 0.0.1-SNAPSHOT | Lettuce Redis 客户端的类型安全扩展 |

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

在您的 `build.gradle.kts` 中添加依赖：

```kotlin
dependencies {
    // Ktor 扩展模块
    implementation("cn.xiaosuli.utils:xiaosuli-ktor:0.0.1-SNAPSHOT")
    
    // Redis Lettuce 扩展模块
    implementation("cn.xiaosuli.utils:xiaosuli-redis-lettuce:0.0.1-SNAPSHOT")
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

## 🛠️ 开发指南

### 项目结构

```
xiaosuli-utils/
├── xiaosuli-ktor/          # Ktor 扩展模块
│   ├── src/main/kotlin/
│   └── build.gradle.kts
├── xiaosuli-redis-lettuce/ # Redis 扩展模块
│   ├── src/main/kotlin/
│   └── build.gradle.kts
├── build.gradle.kts        # 根项目构建配置
├── settings.gradle.kts     # 项目设置
└── README.md              # 项目说明
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