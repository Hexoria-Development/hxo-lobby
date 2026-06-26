package dev.hexoria.hxo.lobby

import dev.hexoria.hxo.base.api.common.state.EventServerState
import dev.slne.surf.redis.RedisApi
import dev.slne.surf.redis.sync.value.SyncValue

val redisLoader = RedisLoader()
val redisApi get() = redisLoader.redisApi

class RedisLoader {
    lateinit var redisApi: RedisApi
    lateinit var eventServerState: SyncValue<EventServerState>

    fun connect() {
        redisApi = RedisApi.create()
        redisApi.createSyncValue<EventServerState>(
            "hxo-event:event-server-state",
            EventServerState.CLOSED
        ).also { eventServerState = it }
        redisApi.freezeAndConnect()
    }

    fun disconnect() {
        redisApi.disconnect()
    }
}