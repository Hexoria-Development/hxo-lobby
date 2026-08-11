package dev.hexoria.hxo.lobby.listener

import dev.hexoria.hxo.lobby.board.HxoScoreboard
import dev.hexoria.hxo.lobby.inventory.item.InventoryItem
import dev.hexoria.hxo.lobby.manager.ElytraBoostManager
import dev.hexoria.hxo.lobby.manager.PushbackManager
import dev.slne.surf.api.paper.scoreboard.SurfAutoUpdatableScoreboard
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object PlayerConnectionListener : Listener {

    private val scoreboards = mutableMapOf<java.util.UUID, SurfAutoUpdatableScoreboard>()

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {

        val player = event.player
        val scoreboard = HxoScoreboard(player)
        scoreboard.enable()
        scoreboard.addViewer(player)
        scoreboards[player.uniqueId] = scoreboard

        event.player.gameMode = GameMode.SURVIVAL
        event.player.inventory.heldItemSlot = 0

        for (i in 0..8) {
            event.player.inventory.clear(i)
        }

        event.player.inventory.setChestplate(null)

        InventoryItem.items.filter { item ->
            item.permission?.let { event.player.hasPermission(it) } ?: true
        }.forEach {
            event.player.inventory.setItem(it.slot, it.getItemForPlayer(event.player))
        }

    }

    @EventHandler
    fun onDisconnect(event: PlayerQuitEvent) {
        scoreboards.remove(event.player.uniqueId)?.disable()
        PushbackManager.remove(event.player.uniqueId)
        ElytraBoostManager.clearBoost(event.player)
    }

    fun reload(player: Player) {
        scoreboards.remove(player.uniqueId)?.disable()
        val scoreboard = HxoScoreboard(player)
        scoreboard.enable()
        scoreboard.addViewer(player)
        scoreboards[player.uniqueId] = scoreboard
    }
}