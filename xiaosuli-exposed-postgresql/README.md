# xiaosuli-exposed-postgresql

Exposed ORM 框架的 PostgreSQL 类型扩展库，为 PostgreSQL 特有数据类型提供类型安全的支持。

## 📦 安装

> **⚠️ 注意（重要）：** 本系列工具库目前尚未发布到 Maven 中央仓库，需要自己下载源码并编译到本地 Maven 仓库后使用。等待后续会上传到 Maven 中央仓库，但目前不是。

编译到本地 Maven 仓库：
```bash
./gradlew publishToMavenLocal
```

在您的 `build.gradle.kts` 中添加依赖：

```kotlin
dependencies {
    implementation("cn.xiaosuli.utils:xiaosuli-exposed-postgresql:0.0.1-SNAPSHOT")
}
```

## 🚀 功能特性

### PostgreSQL INET 类型支持

为 PostgreSQL 的 INET 网络地址类型提供类型安全的支持。

#### INET 类型列定义

```kotlin
import cn.xiaosuli.utils.exposed.inet
import org.jetbrains.exposed.v1.core.Table

object Users : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val ipAddress = inet("ip_address") // 使用 INET 类型
    
    override val primaryKey = PrimaryKey(id)
}
```

#### 使用示例

```kotlin
import cn.xiaosuli.utils.exposed.inet
import org.jetbrains.exposed.v1.sql.Database
import org.jetbrains.exposed.v1.sql.insert
import org.jetbrains.exposed.v1.sql.selectAll
import org.jetbrains.exposed.v1.sql.transactions.transaction

// 连接数据库
Database.connect("jdbc:postgresql://localhost:5432/testdb", driver = "org.postgresql.Driver")

// 插入数据
transaction {
    Users.insert {
        it[name] = "张三"
        it[ipAddress] = "192.168.1.1"
    }
}

// 查询数据
transaction {
    val users = Users.selectAll().toList()
    for (user in users) {
        println("ID: ${user[Users.id]}, Name: ${user[Users.name]}, IP: ${user[Users.ipAddress]}")
    }
}
```

## 📖 API 文档

### 表扩展函数

#### `Table.inet(name: String): Column<String>`

创建一个 PostgreSQL INET 类型的列。

**参数:**
- `name`: 列名

**返回值:** 字符串类型的列对象

### 内部类

#### `InetColumnType`

PostgreSQL INET 类型的列类型实现，继承自 `StringColumnType`。

- **`sqlType(): String`**: 返回 "INET" 作为 SQL 类型名称
- **`setParameter(stmt: PreparedStatementApi, index: Int, value: Any?)`**: 设置 PreparedStatement 参数，将字符串值转换为 PGobject

## 📋 依赖要求

- **Kotlin**: 2.2.20+
- **Exposed**: 1.0.0+
- **PostgreSQL JDBC Driver**: 42.6.0+
- **JDK**: 21+

## 🔗 相关链接

- [主项目 README](../README.md)
- [Exposed 官方文档](https://github.com/JetBrains/Exposed)
- [PostgreSQL 官方文档](https://www.postgresql.org/docs/)

## 📄 许可证

本项目采用 Apache 2.0 许可证 - 查看 [LICENSE](../LICENSE) 文件了解详情。