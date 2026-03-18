package de.hiorcraft.nexLobby.listener

import dev.slne.surf.surfapi.bukkit.api.event.cancel
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent

class DamageListener : Listener {

    @EventHandler
    fun onDamge(event: EntityDamageEvent) {
        if (event.entity is Player) {
            event.cancel()
        }
    }

    @EventHandler
    fun onDamageByEntity(event: EntityDamageByEntityEvent) {
        if (event.entity is Player) {
            event.cancel()
        }
    }
}