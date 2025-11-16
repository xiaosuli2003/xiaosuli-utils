package cn.xiaosuli.utils.redis.lettuce

import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import io.lettuce.core.api.coroutines.RedisCoroutinesCommands
import kotlinx.serialization.json.Json

val DefaultJson = Json { encodeDefaults = true; ignoreUnknownKeys = true }

@OptIn(ExperimentalLettuceCoroutinesApi::class)
suspend fun <T : Any> RedisCoroutinesCommands<String, String>.get(key: RedisKey<T>): T? {
    val raw = this.get(key.key) ?: return null
    return DefaultJson.decodeFromString(key.serializer, raw)
}

@OptIn(ExperimentalLettuceCoroutinesApi::class)
suspend fun <T : Any> RedisCoroutinesCommands<String, String>.set(
    key: RedisKey<T>,
    value: T,
    expireSeconds: Long = 0L
) {
    val encoded = DefaultJson.encodeToString(key.serializer, value)
    if (expireSeconds > 0) {
        this.setex(key.key, expireSeconds, encoded)
    } else {
        this.set(key.key, encoded)
    }
}

@OptIn(ExperimentalLettuceCoroutinesApi::class)
suspend fun RedisCoroutinesCommands<String, String>.del(vararg keys: RedisKey<*>) {
    if (keys.isEmpty()) return
    this.del(*keys.map { it.key }.toTypedArray())
}