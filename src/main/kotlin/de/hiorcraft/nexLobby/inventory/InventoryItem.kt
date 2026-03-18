package de.hiorcraft.nexLobby.inventory

import de.hiorcraft.nexLobby.inventory.item.NavigatorItem
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class InventoryItem(
    val slot: Int,
    val item: ItemStack
) {
    abstract val permission: String?
    abstract fun onInteract(player: Player)

    open fun getItemForPlayer(player: Player): ItemStack = item

    companion object {
        val items = mutableListOf<InventoryItem>()

        init {
            items.add(NavigatorItem)
        }
    }
}