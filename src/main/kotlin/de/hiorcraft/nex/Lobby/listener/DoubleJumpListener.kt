package de.hiorcraft.nex.Lobby.listener

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerToggleFlightEvent

class DoubleJump(): Listener {

    @EventHandler
    fun onPlayerTouch(event: PlayerMoveEvent) {
        val player = event.player


        if (player.gameMode != GameMode.SURVIVAL) return

        val loc = player.location
        val blockUnder = loc.subtract(0.0, 1.0, 0.0).block
        val blockType = blockUnder.type

        if (blockType == Material.AIR) return

        player.allowFlight = true
        player.isFlying = false

        player.fallDistance = 0.0f
    }

    @EventHandler
    fun onDoubleJump(event: PlayerToggleFlightEvent) {
        val player = event.player
        val loc = player.location
        val world = player.world

        if (player.gameMode != GameMode.SURVIVAL) return

        player.spawnParticle(Particle.EXPLOSION, loc, 10, 0.5, 0.5, 0.5, 0.1)

        event.isCancelled = true
        player.allowFlight = false

        player.velocity = player.location.direction.multiply(2).setY(1)
        player.fallDistance = 0.0f
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        if (event.entity is Player && event.cause == EntityDamageEvent.DamageCause.FALL) {
            event.isCancelled = true
        }
    }
}