package dev.hexoria.hxo.lobby.inventory.item.impl.friends

import dev.hexoria.hxo.lobby.inventory.item.InventoryItem
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.builder.SurfComponentBuilder
import dev.slne.surf.api.paper.builder.buildLore
import dev.slne.surf.api.paper.builder.displayName
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemType

object FriendsItem : InventoryItem(7, ItemType.BEACON.createItemStack().apply {

    displayName {
        localColored("Freunde")
    }
    buildLore {
        emptyLine()
        line {
            variableValue("Beschreibung:".toSmallCaps())
        }

        line {
            spacer("» Klicke, um die Freunde liste zu öffen")
        }
    }
}) {
    override val permission: String? = null

    override fun onInteract(player: Player) {
    }
}

private fun SurfComponentBuilder.localColored(text: Any, vararg decoration: TextDecoration) =
    text(text.toString(), TextColor.fromHexString("#42a7f5"), *decoration)
