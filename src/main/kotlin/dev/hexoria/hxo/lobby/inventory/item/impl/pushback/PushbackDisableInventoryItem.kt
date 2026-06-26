package dev.hexoria.hxo.lobby.inventory.item.impl.pushback

import dev.hexoria.hxo.lobby.inventory.item.InventoryItem
import dev.hexoria.hxo.lobby.manager.PushbackManager
import dev.hexoria.hxo.lobby.utils.PermissionRegistry
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.api.core.messages.builder.SurfComponentBuilder
import dev.slne.surf.api.paper.builder.buildLore
import dev.slne.surf.api.paper.builder.displayName
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemType

object PushbackDisableInventoryItem : InventoryItem(2, ItemType.ENDER_EYE.createItemStack().apply {
    displayName {
        variableValue("Pushback")
    }
    buildLore {
        emptyLine()
        line {
            variableValue("Beschreibung:".toSmallCaps())
        }
        line {
            spacer("-")
            appendSpace()
            localColored("Pushback steuern")
        }

        line {
            spacer("-")
            appendSpace()
            localColored("Pushback aktivieren oder deaktivieren")
        }

        emptyLine()

        line {
            variableValue("Status".toSmallCaps())
        }

        line {
            appendSpace()
            spacer("-")
            appendSpace()
            success("Aktiviert", TextDecoration.BOLD)
        }

        line {
            spacer("-")
            appendSpace()
            error("Deaktiviert")
        }

        emptyLine()

        line {
            spacer("» Klicke, um zu wechseln")
        }
    }
}) {
    override val permission: String = PermissionRegistry.PUSHBACK_ITEM

    override fun onInteract(player: Player) {
        if (PushbackManager.isItemOnCooldown(player.uniqueId)) {
            player.sendText {
                appendErrorPrefix()
                error("Du musst noch ${PushbackManager.itemCooldownRemaining(player.uniqueId)}s warten.")
            }
            return
        }

        PushbackManager.setItemCooldown(player.uniqueId)
        PushbackManager.remove(player.uniqueId)
        player.inventory.setItem(slot, PushbackEnableInventoryItem.item)

        player.sendText {
            appendInfoPrefix()
            info("Du hast den Pushback ")
            error("deaktiviert.")
        }
    }
}

private fun SurfComponentBuilder.localColored(text: Any, vararg decoration: TextDecoration) =
    text(text.toString(), TextColor.fromHexString("#f5426c"), *decoration)