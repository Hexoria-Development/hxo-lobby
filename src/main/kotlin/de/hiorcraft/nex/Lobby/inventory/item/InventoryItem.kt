package de.hiorcraft.nex.Lobby.inventory.item

import de.hiorcraft.nex.Lobby.inventory.item.impl.navigator.NavigatorItem
import de.hiorcraft.nex.Lobby.inventory.item.impl.pushback.PushbackDisableInventoryItem
import de.hiorcraft.nex.Lobby.inventory.item.impl.pushback.PushbackEnableInventoryItem
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
            items.add(PushbackDisableInventoryItem)
            items.add(PushbackEnableInventoryItem)
            items.add(NavigatorItem)
        }
    }
}