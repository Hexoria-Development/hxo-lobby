package dev.hexoria.hxo.lobby.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerExpChangeEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerRespawnEvent

object XpBarListener : Listener {

    private fun clearXp(player: org.bukkit.entity.Player) {
        player.exp = 0f              // Balken
        player.level = 0             // Zahl
        player.totalExperience = 0   // Gesamt-XP
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) = clearXp(event.player)

    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) = clearXp(event.player)

    @EventHandler
    fun onExpGain(event: PlayerExpChangeEvent) {
        event.amount = 0
        clearXp(event.player)
    }
}