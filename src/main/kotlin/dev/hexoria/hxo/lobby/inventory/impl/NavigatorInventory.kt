@file:Suppress("UnstableApiUsage")

package dev.hexoria.hxo.lobby.inventory.impl

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.shynixn.mccoroutine.folia.launch
import com.sksamuel.aedile.core.expireAfterWrite
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.buildText
import dev.slne.surf.api.core.messages.adventure.playSound
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.api.paper.builder.buildItem
import dev.slne.surf.api.paper.builder.buildLore
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.inventory.framework.dsl.onItemClick
import dev.slne.surf.api.paper.inventory.framework.dsl.openForPlayer
import dev.slne.surf.api.paper.inventory.framework.dsl.slot
import dev.slne.surf.api.paper.inventory.framework.view.layoutTarget
import dev.slne.surf.api.paper.inventory.framework.view.onFirstRender
import dev.slne.surf.api.paper.inventory.framework.view.paginatedSurfView
import dev.slne.surf.api.paper.inventory.framework.view.pagination.pagination
import dev.slne.surf.api.paper.inventory.framework.view.settings
import dev.slne.surf.api.paper.inventory.framework.view.settings.PaginationViewRows
import dev.slne.surf.core.api.common.SurfCoreApi
import dev.slne.surf.core.api.common.server.state.SurfServerState
import dev.slne.surf.core.api.paper.util.surfPlayer
import me.devnatan.inventoryframework.View
import me.devnatan.inventoryframework.ViewConfigBuilder
import me.devnatan.inventoryframework.context.RenderContext
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.inventory.ItemType
import java.util.*
import kotlin.time.Duration.Companion.minutes

object NavigatorInventory : View() {
    override fun onInit(config: ViewConfigBuilder) {
        config.size(6).cancelInteractions().layout(
            "ASSSAEEEA",
            "ASSSAEEEA",
            "ASSSAEEEA",
            "AAAAAAAAA",
            "LLPPARRCC",
            "LLPPARRCC"
        ).title(buildText {
            text("<shift:-46><glyph:navigator_gui>")
        })
    }
}