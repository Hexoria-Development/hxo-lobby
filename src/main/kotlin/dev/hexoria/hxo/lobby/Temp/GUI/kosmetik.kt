@file:Suppress("UnstableApiUsage")

package dev.hexoria.hxo.lobby.Temp.GUI

import dev.slne.surf.api.core.messages.adventure.buildText
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.inventory.framework.dsl.slot
import dev.slne.surf.api.paper.inventory.framework.dsl.withItem
import me.devnatan.inventoryframework.View
import me.devnatan.inventoryframework.ViewConfigBuilder
import me.devnatan.inventoryframework.context.RenderContext
import org.bukkit.Material

object KosmetikInventory : View() {
    override fun onInit(config: ViewConfigBuilder) {
        config.size(3).cancelInteractions().title(buildText {
            text("Kosmetik")
        })
    }

    override fun onFirstRender(render: RenderContext) {
        render.slot(2, 4) {
            withItem(Material.BARRIER) {
                displayName {
                    error("Du hast noch keine Kosmetik!")
                }
            }
        }
    }
}