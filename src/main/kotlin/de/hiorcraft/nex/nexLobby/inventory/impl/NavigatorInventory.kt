package de.hiorcraft.nex.nexLobby.inventory.impl

import com.github.stefvanschie.inventoryframework.pane.util.Slot
import de.hiorcraft.nex.nexLobby.plugin
import dev.slne.surf.surfapi.bukkit.api.event.cancel
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.menu
import dev.slne.surf.surfapi.bukkit.api.builder.displayName
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.staticPane
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import dev.slne.surf.surfapi.core.api.messages.adventure.text
import org.bukkit.entity.Player


fun navigatorInventory() = menu(text("<shift:-48><glyph:server_selector>"), 6) {
    setOnGlobalDrag { it.cancel() }
    setOnGlobalClick { it.cancel() }

    staticPane(Slot.fromXY(1, 0), 3, 3) {
        fillWith(cosmeticsItem)

        setOnClick {
            val player = it.whoClicked as? Player ?: return@setOnClick

            player.sendText {
                appendSuccessPrefix()
                append(text("Du hast den Navigator geöffnet!"))
            }
            player.closeInventory()
        }
    }
}

private val cosmeticsItem = plugin.getLobbyItem().apply {
    displayName {
        primary("???")
    }
}