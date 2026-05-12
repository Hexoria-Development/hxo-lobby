package de.hiorcraft.nex.Lobby.inventory.item.impl.navigator

import de.hiorcraft.nex.Lobby.inventory.item.InventoryItem
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.builder.SurfComponentBuilder
import dev.slne.surf.api.paper.builder.buildLore
import dev.slne.surf.api.paper.builder.displayName
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemType

object NavigatorItem : InventoryItem(4, ItemType.COMPASS.createItemStack().apply {
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
    }
}

private fun SurfComponentBuilder.localColored(text: Any, vararg decoration: TextDecoration) =
    text(text.toString(), TextColor.fromHexString("#f58442"), *decoration)