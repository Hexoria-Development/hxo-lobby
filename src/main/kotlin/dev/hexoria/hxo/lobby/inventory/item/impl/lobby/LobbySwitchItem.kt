package dev.hexoria.hxo.lobby.inventory.item.impl.lobby

import com.github.shynixn.mccoroutine.folia.launch
import dev.hexoria.hxo.lobby.inventory.item.InventoryItem
import dev.hexoria.hxo.lobby.plugin
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.api.core.messages.builder.SurfComponentBuilder
import dev.slne.surf.api.paper.builder.buildLore
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.core.api.common.SurfCoreApi
import dev.slne.surf.core.api.paper.util.surfPlayer
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemType
import java.util.UUID

object LobbySwitchItem : InventoryItem(1, ItemType.ENDER_PEARL.createItemStack().apply {
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

        player.sendText {
            appendInfoPrefix()
            info("Du wirst zum Hub gesendet...")
        }

        val servers = SurfCoreApi.getServerByCategory("Lobby")
            .sortedBy { it.getPlayerCount() }

        if (servers.isEmpty()) {
            player.sendText {
                appendErrorPrefix()
                error("Es konnte kein passender Hub-Server gefunden werden.")
            }
            return
        }

        plugin.launch {
            var success = false

            for (server in servers) {
                val result = SurfCoreApi.sendPlayerAwaiting(player.surfPlayer, server)
                if (result.isSuccessful()) {
                    success = true
                    break
                }
            }

            if (success) {
                player.sendText {
                    appendInfoPrefix()
                    success("Du wurdest erfolgreich zum Hub gesendet.")
                }
            } else {
                player.sendText {
                    appendErrorPrefix()
                    error("Es konnte kein passender Hub-Server gefunden werden.")
                }
            }
        }
    }
}

private fun SurfComponentBuilder.localColored(text: Any, vararg decoration: TextDecoration) =
    text(text.toString(), TextColor.fromHexString("#42a7f5"), *decoration)
