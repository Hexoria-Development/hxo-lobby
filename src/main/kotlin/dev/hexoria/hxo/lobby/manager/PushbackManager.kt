package dev.hexoria.hxo.lobby.manager

import dev.hexoria.hxo.lobby.plugin
import dev.hexoria.hxo.lobby.utils.PermissionRegistry
import dev.slne.surf.api.core.util.mutableObjectSetOf
import dev.slne.surf.api.paper.util.toPlayers
import org.bukkit.Bukkit
import org.bukkit.Effect
import java.util.UUID

object PushbackManager {
    private val pushbacks = mutableObjectSetOf<UUID>()
    private val itemCooldowns = HashMap<UUID, Long>()
    private const val RANGE = 3.0
    private const val FORCE = -0.5
    private const val Y_FORCE = 0.5
    private const val ITEM_COOLDOWN_MS = 3_000L

    fun startTask() {
        Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, {
            pushbacks.toPlayers().forEach { player ->
                val nearbyPlayers = player.location.getNearbyPlayers(RANGE) { other ->
                    other != player && !other.hasPermission(PermissionRegistry.PUSHBACK_ITEM)
                }

                for (nearby in nearbyPlayers) {
                    nearby.velocity = player.location.toVector()
                        .subtract(nearby.location.toVector())
                        .multiply(FORCE)
                        .setY(Y_FORCE)
                }

                player.world.playEffect(player.location, Effect.ENDER_SIGNAL, null)
            }
        }, 10, 10)
    }

    fun add(uuid: UUID) {
        pushbacks.add(uuid)
    }

    fun remove(uuid: UUID) {
        pushbacks.remove(uuid)
    }

    fun isItemOnCooldown(uuid: UUID): Boolean {
        val lastUse = itemCooldowns[uuid] ?: return false
        return System.currentTimeMillis() - lastUse < ITEM_COOLDOWN_MS
    }

    fun itemCooldownRemaining(uuid: UUID): Long {
        val lastUse = itemCooldowns[uuid] ?: return 0L
        return ((ITEM_COOLDOWN_MS - (System.currentTimeMillis() - lastUse) + 999) / 1000)
    }

    fun setItemCooldown(uuid: UUID) {
        itemCooldowns[uuid] = System.currentTimeMillis()
    }
}