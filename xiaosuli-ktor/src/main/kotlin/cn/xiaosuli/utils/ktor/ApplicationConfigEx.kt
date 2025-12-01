@file:Suppress("unused")

package cn.xiaosuli.utils.ktor

import io.ktor.server.config.*

/**
 * 从[ApplicationConfig]获取[key]对应的值，如果值为空，则返回默认值
 *
 * @return [key]对应的值
 */
fun ApplicationConfig.getStringOrDefault(key: String, defaultValue: String) =
    tryGetString(key)?.takeIf { it.isNotBlank() } ?: defaultValue

/**
 * 从[ApplicationConfig]获取[key]对应的值，如果值为空，则返回默认值
 *
 * @return [key]对应的值
 */
fun ApplicationConfig.getLongOrDefault(key: String, defaultValue: Long): Long {
    return tryGetString(key)
        ?.takeIf { it.isNotBlank() }
        ?.toLongOrNull()
        ?: defaultValue
}

/**
 * 从[ApplicationConfig]获取[key]对应的值，如果值为空，则返回默认值
 *
 * @return [key]对应的值
 */
fun ApplicationConfig.getIntOrDefault(key: String, defaultValue: Int): Int {
    return tryGetString(key)
        ?.takeIf { it.isNotBlank() }
        ?.toIntOrNull()
        ?: defaultValue
}
