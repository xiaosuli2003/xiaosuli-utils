@file:Suppress("unused")

package cn.xiaosuli.utils.ktor

import io.ktor.server.application.ApplicationCall
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.response.respond
import io.ktor.util.*
import kotlinx.serialization.json.Json

/**
 * 将请求中的查询参数转换为指定类型
 * TODO: 这只是一个简单的实现，后续可以优化
 *
 * @param T 目标类型
 * @return 转换后的对象
 */
inline fun <reified T> ApplicationCall.requestQueryParameters(): T {
    try {
        // 将查询参数转换为Map
        val paramsToMap = parameters.toMap().mapValues { (_, value) ->
            if (value.size == 1) value[0] else value.toString()
        }
        // 将Map转换为Json字符串
        val encodeToString = Json.encodeToString(paramsToMap)
        // 将Json字符串转换为指定类型
        return Json.decodeFromString<T>(encodeToString)
    } catch (e: BadRequestException) {
        e.printStackTrace()
        throw BadRequestException("缺少必要的查询参数或参数格式错误！")
    }
}

/**
 * 统一成功响应
 *
 * @param data 数据
 * @param httpStatus 状态码
 * @param message 提示信息
 */
suspend inline fun <reified T> ApplicationCall.respondOk(
    data: T? = null,
    httpStatus: HttpCode = HttpCode.Success,
    message: String? = null
) {
    respond(R.ok(data, httpStatus, message))
}

/**
 * 统一失败响应
 *
 * @param httpStatus 状态码
 * @param message 提示信息
 */
suspend fun ApplicationCall.respondFail(
    httpStatus: HttpCode,
    message: String? = null
) {
    respond(R.fail(httpStatus, message))
}
