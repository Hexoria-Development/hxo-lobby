package de.hiorcraft.nex.Lobby.hook.npc

import de.hiorcraft.nex.Lobby.plugin
import de.hiorcraft.nex.Lobby.utils.Locations
import dev.slne.surf.npc.api.dsl.npc
import dev.slne.surf.npc.api.npc.Npc
import dev.slne.surf.npc.api.npc.rotation.NpcRotationType
import dev.slne.surf.surfapi.core.api.font.toSmallCaps
import io.papermc.paper.threadedregions.scheduler.ScheduledTask
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import java.util.concurrent.TimeUnit

object SurfNpcHook {
    lateinit var eventNPC: Npc
    lateinit var shopNPC: Npc

    fun initialize() {
        createEventNpc()

        plugin.logger.info("Successfully loaded surf-npc integration.")
    }

    private fun createEventNpc() {
        eventNPC = npc {
            displayName = {
                note("Event".toSmallCaps(), TextDecoration.BOLD)
            }
            type = EntityType.MANNEQUIN
            uniqueName = "event_npc"
            skin = SurfNpcSkins.EVENT.getSkin()

            location = Locations.EVENT_NPC.getLocation()

            rotationType = NpcRotationType.PER_PLAYER
        }
    }

    private lateinit var syncTask: ScheduledTask

    fun startSyncTask() {
        syncTask = Bukkit.getAsyncScheduler().runAtFixedRate(plugin, {

            eventNPC.refresh()
        }, 0L, 30L, TimeUnit.SECONDS)
    }

    fun stopSyncTask() {
        if (::syncTask.isInitialized) {
            syncTask.cancel()
        }
    }
}

