package dev.hexoria.hxo.lobby

import dev.slne.surf.redis.RedisApi
import dev.slne.surf.redis.sync.value.SyncValue
import dev.hexoria.hxo.lobby.hook.npc.SurfNpcHook

val redisLoader = RedisLoader()
val redisApi get() = redisLoader.redisApi

val BINGO_SERVERS = listOf("bingo01", "bingo02", "bingo03")

class RedisLoader {
    lateinit var redisApi: RedisApi

    val bingoServerStates = mutableMapOf<String, SyncValue<String>>()

    fun connect() {
        redisApi = RedisApi.create()

        BINGO_SERVERS.forEach { serverName ->
            redisApi.createSyncValue<String>(
                "bingo:status:$serverName",
                "OFFLINE"
            ).also { syncValue ->
                bingoServerStates[serverName] = syncValue
                syncValue.addListener { _ ->
                    // NPC neu rendern wenn sich ein Status ändert
                    if (SurfNpcHook.isBingoNpcInitialized()) {
                        SurfNpcHook.bingoNPC.refresh()
                    }
                }
            }
        }

        redisApi.freezeAndConnect()
    }

    fun disconnect() {
        redisApi.disconnect()
    }
}