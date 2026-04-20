package de.hiorcraft.nex.Lobby.listener

import de.hiorcraft.nex.Lobby.lobbyConfigHolder
import de.hiorcraft.nex.Lobby.manager.ElytraBoostManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

object PlayerMoveListener : Listener {

    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        val player = event.player
        val lobbyConfig = lobbyConfigHolder.lobbyConfig
        val minHeight = lobbyConfig.minHeight

        if (!event.hasExplicitlyChangedPosition()) {
            return
        }

        if (player.location.y < minHeight) {
            player.teleportAsync(lobbyConfig.spawnPoint.toLocation()).thenRun {
                player.velocity.setY(0)
            }
            return
        }

        @Suppress("DEPRECATION")
        if (player.isOnGround) {
            ElytraBoostManager.clearBoost(player)
        }
    }
}