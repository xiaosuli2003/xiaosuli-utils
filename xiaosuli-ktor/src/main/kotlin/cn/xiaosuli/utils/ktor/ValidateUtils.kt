@file:Suppress("unused")

package cn.xiaosuli.utils.ktor

import io.ktor.server.plugins.requestvalidation.*

/**
 * 验证字符串是否为空
 * * 如果字符串为空，则返回一个`Invalid`验证结果
 * * 否则返回一个`Valid`验证结果
 */
fun validateNotBlank(value: String, message: String): ValidationResult =
    if (value.isBlank()) {
        ValidationResult.Invalid(message)
    } else {
        ValidationResult.Valid
    }

/**
 * 验证字符串的长度是否在指定范围内
 * * 如果字符串的长度不符合要求，则返回一个`Invalid`验证结果
 * * 否则返回一个`Valid`验证结果
 */
fun validateSize(value: String, min: Int = 0, max: Int, message: String) =
    if (value.length !in min..max) {
        ValidationResult.Invalid(message)
    } else {
        ValidationResult.Valid
    }

/**
 * `ValidationResult`的扩展函数，用于合并两个验证结果
 * * 如果当前验证结果是`Invalid`，则返回当前验证结果
 * * 如果当前验证结果是`Valid`，则返回另一个验证结果
 */
fun ValidationResult.and(other: ValidationResult): ValidationResult {
    return this as? ValidationResult.Invalid ?: other
}
