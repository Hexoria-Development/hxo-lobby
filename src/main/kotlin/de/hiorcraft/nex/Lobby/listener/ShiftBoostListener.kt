package de.hiorcraft.nex.Lobby.listener

import de.hiorcraft.nex.Lobby.manager.ElytraBoostManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleSneakEvent

object ShiftBoostListener : Listener {
    @EventHandler
    fun onShift(event: PlayerToggleSneakEvent) {
        if (!event.isSneaking) {
            return
        }

        ElytraBoostManager.checkAndBoost(event.player)
    }
}

