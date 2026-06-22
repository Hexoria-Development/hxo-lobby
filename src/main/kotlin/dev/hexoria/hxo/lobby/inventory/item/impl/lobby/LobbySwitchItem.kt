package dev.hexoria.hxo.lobby.inventory.item.impl.lobby

import dev.hexoria.hxo.lobby.inventory.item.InventoryItem
import dev.hexoria.hxo.lobby.lobbyConfigHolder
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.api.core.messages.builder.SurfComponentBuilder
import dev.slne.surf.api.paper.SurfApiPaper
import dev.slne.surf.api.paper.builder.buildLore
import dev.slne.surf.api.paper.builder.displayName
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemType

object LobbySwitchItem : InventoryItem(0, ItemType.SPYGLASS.createItemStack().apply {
    displayName {
        localColored("Lobby wechseln")
    }

    buildLore {
        emptyLine()
        line {
            variableValue("Beschreibung:".toSmallCaps())
        }

        line {
            spacer("» Klicke, um die Lobby zu wechseln")
        }
    }
}) {
    override val permission: String? = null

    override fun onInteract(player: Player) {
        val target = lobbyConfigHolder.lobbyConfig.otherLobbyServer

        player.sendText {
            appendSuccessPrefix()
            success("Du wirst zur Lobby ")
            variableValue("'$target'")
            success(" verbunden ...")
        }

        SurfApiPaper.sendPlayerToServer(player, target)
    }
}

private fun SurfComponentBuilder.localColored(text: Any, vararg decoration: TextDecoration) =
    text(text.toString(), TextColor.fromHexString("#42a7f5"), *decoration)
