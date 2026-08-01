package dev.hexoria.hxo.lobby.inventory.item.impl.kosmetik

import dev.hexoria.hxo.lobby.Temp.GUI.KosmetikInventory
import dev.hexoria.hxo.lobby.hook.profile.ProfileHook
import dev.hexoria.hxo.lobby.inventory.item.InventoryItem
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.builder.SurfComponentBuilder
import dev.slne.surf.api.paper.builder.buildLore
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.inventory.framework.open
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemType

object KosmetikItem : InventoryItem(4, ItemType.CHEST.createItemStack().apply {

    displayName {
        localColored("Kosmetik")
    }
    buildLore {
        emptyLine()
        line {
            variableValue("Beschreibung:".toSmallCaps())
        }

        line {
            spacer("» Klicke, um deine Kosmetik zu sehen")
        }
    }
}) {
    override val permission: String? = null

    override fun onInteract(player: Player) {
        KosmetikInventory.open(player)
    }
}

private fun SurfComponentBuilder.localColored(text: Any, vararg decoration: TextDecoration) =
    text(text.toString(), TextColor.fromHexString("#42a7f5"), *decoration)