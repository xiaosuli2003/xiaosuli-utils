package cn.xiaosuli.utils.redis.lettuce

import kotlinx.serialization.KSerializer

/**
 * 类型安全的 Redis Key。
 * 每个 key 绑定一个具体的序列化器，确保编解码一致。
 *
 * @param key Redis Key
 * @param serializer 序列化器
 */
class RedisKey<T : Any> internal constructor(
    val key: String,
    internal val serializer: KSerializer<T>
) {
    override fun toString(): String = key

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RedisKey<*>) return false
        return key == other.key
    }

    override fun hashCode(): Int = key.hashCode()
}