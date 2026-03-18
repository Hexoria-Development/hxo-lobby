package de.hiorcraft.nexLobby.inventory.impl

import com.github.stefvanschie.inventoryframework.pane.util.Slot
import de.hiorcraft.nexLobby.inventory.item.NavigatorItem
import dev.slne.surf.surfapi.bukkit.api.event.cancel
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.menu
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.staticPane
import dev.slne.surf.surfapi.bukkit.api.surfBukkitApi
import dev.slne.surf.surfapi.core.api.messages.adventure.text
import org.bukkit.entity.Player

fun navigatorInventory() = menu(text("<shift:-48><glyph:server_selector>"), 6) {
    setOnGlobalDrag { it.cancel() }
    setOnGlobalClick { it.cancel() }

    staticPane(Slot.fromXY(1, 0), 3, 3) {
        fillWith(NavigatorItem.item)

        setOnClick {
            val player = it.whoClicked as? Player ?: return@setOnClick
            surfBukkitApi.sendPlayerToServer(player, "survival")
        }
    }
}