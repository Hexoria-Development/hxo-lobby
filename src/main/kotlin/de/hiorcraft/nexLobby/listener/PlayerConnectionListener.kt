package de.hiorcraft.nexLobby.listener

import de.hiorcraft.nexLobby.inventory.InventoryItem
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object PlayerConnectionListener: Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        event.player.gameMode = GameMode.ADVENTURE
        event.player.inventory.heldItemSlot = 4

        for (i in 0..8) {
            event.player.inventory.clear(i)
        }

        InventoryItem.items.filter { item ->
            item.permission?.let { event.player.hasPermission(it) } ?: true
        }.forEach {
            event.player.inventory.setItem(it.slot, it.getItemForPlayer(event.player))
        }
    }
}