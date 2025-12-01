@file:Suppress("unused")

package cn.xiaosuli.utils.ktor

import io.swagger.v3.oas.annotations.media.Schema
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * 统一响应结果
 *
 * @param code 状态码
 * @param msg 提示信息
 * @param data 数据
 * @param timestamp 时间戳
 */
@Serializable
@Schema(description = "统一响应结果实体")
class HttpResponse<T>(
    @Schema(description = "状态码")
    val code: Int,
    @Schema(description = "提示信息")
    val msg: String,
    @Schema(description = "数据")
    val data: T? = null,
    @Schema(description = "时间戳")
    val timestamp: Long
)

object R {
    /**
     * 统一成功响应
     *
     * @param data 数据
     * @param httpStatus 状态码
     * @param message 提示信息
     * @return 统一响应结果
     */
    @OptIn(ExperimentalTime::class)
    fun <T> ok(
        data: T? = null,
        httpStatus: HttpCode = HttpCode.Success,
        message: String? = null
    ): HttpResponse<T> {
        val timestamp = Clock.System.now().toEpochMilliseconds()
        return HttpResponse(httpStatus.value, message ?: httpStatus.reasonPhrase, data, timestamp)
    }

    /**
     * 统一失败响应
     *
     * @param httpStatus 状态码
     * @param message 提示信息
     * @return 统一响应结果
     */
    @OptIn(ExperimentalTime::class)
    fun fail(
        httpStatus: HttpCode,
        message: String? = null
    ): HttpResponse<Unit> {
        val timestamp = Clock.System.now().toEpochMilliseconds()
        return HttpResponse(httpStatus.value, message ?: httpStatus.reasonPhrase, null, timestamp)
    }
}
