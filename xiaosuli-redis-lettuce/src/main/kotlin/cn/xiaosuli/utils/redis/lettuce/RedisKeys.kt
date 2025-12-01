@file:Suppress("unused")

package cn.xiaosuli.utils.redis.lettuce

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.builtins.serializer

// ====== 基础类型（无需 @Serializable）======

fun stringRedisKey(name: String): RedisKey<String> =
    RedisKey(name, String.serializer())

fun booleanRedisKey(name: String): RedisKey<Boolean> =
    RedisKey(name, Boolean.serializer())

fun intRedisKey(name: String): RedisKey<Int> =
    RedisKey(name, Int.serializer())

fun longRedisKey(name: String): RedisKey<Long> =
    RedisKey(name, Long.serializer())

fun doubleRedisKey(name: String): RedisKey<Double> =
    RedisKey(name, Double.serializer())

fun stringSetRedisKey(name: String): RedisKey<Set<String>> =
    RedisKey(name, SetSerializer(String.serializer()))

// ====== 自定义对象（需 @Serializable）======

fun <T : Any> redisKey(name: String, serializer: KSerializer<T>): RedisKey<T> =
    RedisKey(name, serializer)