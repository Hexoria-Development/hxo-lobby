package de.hiorcraft.nex.nexLobby.Hook.nexo

import com.nexomc.nexo.api.NexoItems
import org.bukkit.inventory.ItemType

object NexoHook {
    fun getLobbyItem() =
        NexoItems.itemFromId("lobby")?.build() ?: ItemType.PAPER.createItemStack()
}

