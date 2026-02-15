package de.hiorcraft.nex.nexLobby.listener

import dev.slne.surf.surfapi.bukkit.api.event.cancel
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent

class FoodListener : Listener {

    @EventHandler
    fun onFoodlose(event: FoodLevelChangeEvent) {
        if (event.entity is Player) {
            event.cancel()
        }
    }
}