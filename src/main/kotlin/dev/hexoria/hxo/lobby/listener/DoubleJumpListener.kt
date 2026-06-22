package dev.hexoria.hxo.lobby.listener

import dev.hexoria.hxo.lobby.hook.parkour.HxoParkourHook
import dev.slne.surf.api.core.util.mutableObject2ObjectMapOf
import org.bukkit.GameMode
import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInputEvent
import java.util.*

object DoubleJumpListener : Listener {
    private val lastJump = mutableObject2ObjectMapOf<UUID, Long>()
    private const val DOUBLE_JUMP_WINDOW = 400L

    @EventHandler
    fun onInput(event: PlayerInputEvent) {
        val player = event.player

        if (!event.input.isJump) {
            return
        }

        if (player.gameMode == GameMode.CREATIVE || player.gameMode == GameMode.SPECTATOR) {
            return
        }

        if (HxoParkourHook.isRunning(player)) {
            return
        }

        val now = System.currentTimeMillis()
        val last = lastJump[player.uniqueId]

        if (player.isOnGround) {
            lastJump[player.uniqueId] = now
            return
        }

        if (last != null && now - last <= DOUBLE_JUMP_WINDOW) {
            val direction = player.eyeLocation.direction
            val loc = player.location

            player.velocity = direction.multiply(2).setY(1)
            loc.world.spawnParticle(Particle.EXPLOSION, loc, 10)

            lastJump.remove(player.uniqueId)
        }
    }
}