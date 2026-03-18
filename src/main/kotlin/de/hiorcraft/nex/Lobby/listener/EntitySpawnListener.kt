package de.hiorcraft.nex.Lobby.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntitySpawnEvent

object EntitySpawnListener : Listener {

    @EventHandler
    fun onSpawn(event: EntitySpawnEvent) {
        event.isCancelled = true
    }
}