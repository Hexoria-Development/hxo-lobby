package de.hiorcraft.nex.Lobby.listener

import de.hiorcraft.nex.Lobby.utils.PermissionRegistry
import dev.slne.surf.api.core.messages.adventure.playSound
import dev.slne.surf.api.paper.event.cancel
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PushbackListener: Listener {
    private val PUSHBACK_FORCE = 3.0
    private val PUSHBACK_Y_FORCE = 0.35

    @EventHandler
    fun onAttack(event: PrePlayerAttackEntityEvent) {
        val player = event.player
        val attacked = event.attacked as? Player ?: return

        if (!player.isSneaking) {
            return
        }

        if (!player.hasPermission(PermissionRegistry.PUSHBACK_ATTACK) && !player.hasPermission(PermissionRegistry.PUSHBACK_ITEM)) {
            return
        }

        event.cancel()

        val knockback = attacked.location.toVector()
            .subtract(player.location.toVector())
            .normalize()
            .multiply(PUSHBACK_FORCE)
            .setY(PUSHBACK_Y_FORCE)

        attacked.velocity = knockback

        player.playSound(true) {
            type(Sound.ENTITY_PLAYER_ATTACK_CRIT)
            volume(0.2f)
        }
    }
}