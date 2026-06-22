package dev.hexoria.hxo.lobby.listener

import dev.hexoria.hxo.lobby.lobbyConfigHolder
import io.papermc.paper.event.player.AsyncPlayerSpawnLocationEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object SpawnLocationListener : Listener {
    @EventHandler
    fun onConnect(event: AsyncPlayerSpawnLocationEvent) {
        event.spawnLocation = lobbyConfigHolder.lobbyConfig.spawnPoint.toLocation()
    }
}