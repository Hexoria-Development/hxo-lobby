package dev.hexoria.hxo.lobby.inventory.item

import dev.hexoria.hxo.lobby.inventory.item.impl.friends.FriendsItem
import dev.hexoria.hxo.lobby.inventory.item.impl.profile.ProfileItem
import dev.hexoria.hxo.lobby.inventory.item.impl.pushback.*
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
            items.add(ProfileItem)
        }
    }
}