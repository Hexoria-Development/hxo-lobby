package de.hiorcraft.nex.Lobby.listener

import de.hiorcraft.nex.Lobby.lobbyConfigHolder
import io.papermc.paper.event.player.AsyncPlayerSpawnLocationEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object SpawnLocationListener : Listener {
    @EventHandler
    fun onConnect(event: AsyncPlayerSpawnLocationEvent) {
        event.spawnLocation = lobbyConfigHolder.lobbyConfig.spawnPoint.toLocation()
    }
}