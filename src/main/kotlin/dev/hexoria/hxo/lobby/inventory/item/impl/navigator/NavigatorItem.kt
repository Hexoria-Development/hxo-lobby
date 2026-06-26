package dev.hexoria.hxo.lobby.inventory.item.impl.navigator

import dev.hexoria.hxo.lobby.inventory.impl.NavigatorInventory
import dev.hexoria.hxo.lobby.inventory.item.InventoryItem
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.builder.SurfComponentBuilder
import dev.slne.surf.api.paper.builder.buildLore
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.inventory.framework.open
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemType

object NavigatorItem : InventoryItem(0, ItemType.COMPASS.createItemStack().apply {
    displayName {
        localColored("Navigator")
    }

    buildLore {
        emptyLine()
        line {
            variableValue("Beschreibung:".toSmallCaps())
        }

        line {
            spacer("» Klicke, um den Navigator zu öffnen")
        }
    }
}) {
    override val permission = null
    override fun onInteract(player: Player) {
        NavigatorInventory.open(player)
    }
}

private fun SurfComponentBuilder.localColored(text: Any, vararg decoration: TextDecoration) =
    text(text.toString(), TextColor.fromHexString("#42a7f5"), *decoration)