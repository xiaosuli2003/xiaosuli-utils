@file:Suppress("unused")

package cn.xiaosuli.utils.ktor

/**
 * HTTP状态码枚举
 *
 * @property value 状态码
 * @property reasonPhrase 状态码描述
 */
enum class HttpCode(
    val value: Int,
    val reasonPhrase: String
) {
    /** 2233 */
    Success(2233, "操作成功"),

    /** 400 */
    BadRequest(400, "参数列表错误（缺少，格式不匹配）"),

    /** 401 */
    Unauthorized(401, "未授权"),

    /** 403 */
    Forbidden(403, "访问受限，授权过期"),

    /** 404 */
    NotFound(404, "资源，服务未找到"),

    /** 405 */
    MethodNotAllowed(405, "不允许的HTTP方法"),

    /** 500 */
    Error(500, "系统内部错误")
}
