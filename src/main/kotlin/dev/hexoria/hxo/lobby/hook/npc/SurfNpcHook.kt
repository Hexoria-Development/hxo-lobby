package dev.hexoria.hxo.lobby.hook.npc

import com.github.shynixn.mccoroutine.folia.launch
import dev.hexoria.hxo.base.api.common.state.EventServerState
import dev.hexoria.hxo.lobby.eventServerAccess
import dev.hexoria.hxo.lobby.lobbyConfigHolder
import dev.hexoria.hxo.lobby.plugin
import dev.hexoria.hxo.lobby.utils.Locations
import dev.hexoria.hxo.lobby.utils.PermissionRegistry
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.core.api.common.SurfCoreApi
import dev.slne.surf.core.api.paper.util.surfPlayer
import dev.slne.surf.npc.api.dsl.npc
import dev.slne.surf.npc.api.event.NpcInteractEvent
import dev.slne.surf.npc.api.npc.Npc
import dev.slne.surf.npc.api.npc.rotation.NpcRotationType
import dev.slne.surf.queue.api.queue
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import java.util.UUID

object SurfNpcHook {

    var eventServerDisplayName: String = "Event"

    lateinit var eventNPC: Npc
    lateinit var shopNPC: Npc
    lateinit var smashNPC: Npc
    lateinit var bingoNPC: Npc
    lateinit var ribNPC: Npc
    lateinit var rubineNPC: Npc
    lateinit var MurderNPC: Npc

    fun initialize() {
        createEventNpc()
        createShopNPc()
        createSmashNPC()
        createBingNPC()
        createIrbNpc()
        createRubineNPC()
        createMurderNPC()

        plugin.logger.info("Successfully loaded surf-npc integration.")
    }

    private fun createEventNpc() {
        eventNPC = npc {
            displayName = {
                useTransparentNametagBackground = true
                error(eventServerDisplayName.toSmallCaps(), TextDecoration.BOLD)
                appendNewline()
                appendNewline()
                secondary("26.1.2")
                appendNewline()
            }
            type = EntityType.MANNEQUIN
            uniqueName = "event_npc"
            skin = SurfNpcSkins.EVENT.getSkin()

            location = Locations.EVENT_NPC.getLocation()

            rotationType = NpcRotationType.PER_PLAYER

            withEventHandler<NpcInteractEvent> {
                val player = it.player

                when (eventServerAccess.getEventServerState()) {
                    EventServerState.OPEN -> {
                        queueToEventServer(player)
                        return@withEventHandler
                    }

                    EventServerState.CLOSED -> {
                        if (player.hasPermission(PermissionRegistry.EVENT_BYPASS)) {
                            queueToEventServer(player)
                            return@withEventHandler
                        }
                        player.sendText {
                            appendErrorPrefix()
                            error("Der Event Server ist aktuell geschlossen!")
                        }
                    }

                    EventServerState.UNKNOWN -> {
                        player.sendText {
                            appendErrorPrefix()
                            error("Aktuell findet kein Event statt!")
                        }
                    }

                    EventServerState.WATING -> {
                        if (player.hasPermission(PermissionRegistry.EVENT_BYPASS)) {
                            queueToEventServer(player)
                            return@withEventHandler
                        }
                        player.sendText {
                            appendErrorPrefix()
                            error("Der Event Server wird aktuell vorbereitet!")
                        }
                    }
                }
            }
        }
    }

    private fun createShopNPc() {
        shopNPC = npc {
            displayName = {
                useTransparentNametagBackground = true
                note("Shop".toSmallCaps(), TextDecoration.BOLD)
                appendNewline()
                warning("Täglich neue Kosmetika")
                appendNewline()
            }
            type = EntityType.MANNEQUIN
            uniqueName = "shop_npc"
            skin = SurfNpcSkins.SHOP.getSkin()

            location = Locations.SHOP_NPC.getLocation()

            rotationType = NpcRotationType.PER_PLAYER

            withEventHandler<NpcInteractEvent> {
                it.player.sendText {
                    warning("[Shop]")
                    appendSpace()
                    darkSpacer("|")
                    appendSpace()
                    error("Es ist ein Fehler aufgetreten!")
                }
            }
        }
    }

    private fun createSmashNPC() {
        smashNPC = npc {
            displayName = {
                useTransparentNametagBackground = true
                warning("smash".toSmallCaps(), TextDecoration.BOLD)
                appendNewline()
                appendNewline()
                success("» Neue «")
                appendNewline()
            }
            type = EntityType.MANNEQUIN
            uniqueName = "smash_npc"
            skin = SurfNpcSkins.SMASH.getSkin()

            location = Locations.SMASH_NPC.getLocation()
            rotationType = NpcRotationType.PER_PLAYER
        }
    }

    private fun createIrbNpc() {
        ribNPC = npc {
            displayName = {
                useTransparentNametagBackground = true
                warning("Random Item Battle".toSmallCaps(), TextDecoration.BOLD)
                appendNewline()
                appendNewline()
                success("» Neue «")
                appendNewline()
            }
            type = EntityType.MANNEQUIN
            uniqueName = "rib_npc"
            skin = SurfNpcSkins.RIB.getSkin()

            location = Locations.RIB_NPC.getLocation()
            rotationType = NpcRotationType.PER_PLAYER
        }
    }

    private fun createBingNPC() {
        bingoNPC = npc {
            displayName = {
                useTransparentNametagBackground = true
                warning("Bingo".toSmallCaps(), TextDecoration.BOLD)
                appendNewline()
                appendNewline()
                success("» Neue «")
                appendNewline()
            }
            type = EntityType.MANNEQUIN
            uniqueName = "bingo_npc"
            skin = SurfNpcSkins.BINGO.getSkin()

            location = Locations.UHC_NPC.getLocation()
            rotationType = NpcRotationType.PER_PLAYER
        }
    }

    private fun createRubineNPC() {
        rubineNPC = npc {
            displayName = {
                useTransparentNametagBackground = true
                note("Tägliche Belohnung".toSmallCaps(), TextDecoration.BOLD)
                appendNewline()
                warning("Die nächste ist in -000 Tagen")
                appendNewline()
            }
            type = EntityType.MANNEQUIN
            uniqueName = "rubine_npc"
            skin = SurfNpcSkins.RUBINE.getSkin()

            location = Locations.RUBINE_NPC.getLocation()
            rotationType = NpcRotationType.PER_PLAYER

            withEventHandler<NpcInteractEvent> {
                it.player.sendText {
                    warning("[Shop]")
                    appendSpace()
                    darkSpacer("|")
                    appendSpace()
                    error("Es ist ein Fehler bei deiner Belohnung aufgetreten!")
                }
            }
        }
    }

    private fun createMurderNPC() {
        MurderNPC = npc {
            displayName = {
                useTransparentNametagBackground = true
                warning("Murder".toSmallCaps(), TextDecoration.BOLD)
                appendNewline()
                appendNewline()
                success("» Neue «")
                appendNewline()
            }
            type = EntityType.MANNEQUIN
            uniqueName = "murder_npc"
            skin = SurfNpcSkins.MURDER.getSkin()

            location = Locations.MURDER_NPC.getLocation()
            rotationType = NpcRotationType.PER_PLAYER
        }
    }

    fun removeFromEventQueue(uuid: UUID) {
        val server = SurfCoreApi.getServerByName(lobbyConfigHolder.lobbyConfig.eventServerName) ?: return

        plugin.launch {
            server.queue().dequeue(uuid)
        }
    }
}

private fun queueToEventServer(player: Player) {
    SurfCoreApi.getServerByName(lobbyConfigHolder.lobbyConfig.eventServerName)
        ?.let { server ->
            plugin.launch {
                if (player.hasPermission(PermissionRegistry.EVENT_BYPASS)) {
                    player.sendText {
                        appendInfoPrefix()
                        info("Du hast die Warteschlange umgangen und wirst nun mit dem Event Server verbunden...")
                    }
                    val status = SurfCoreApi.sendPlayerAwaiting(player.surfPlayer, server)

                    if (status.isSuccessful()) {
                        player.sendText {
                            appendSuccessPrefix()
                            success("Du wurdest erfolgreich zum Event Server teleportiert.")
                        }
                    } else {
                        player.sendText {
                            appendErrorPrefix()
                            error("Es gab ein Problem beim Teleportieren zum Event Server: ${status.status}")
                        }
                    }

                    return@launch
                }

                val success = server.queue().enqueue(player.uniqueId)

                if (success) {
                    player.sendText {
                        appendSuccessPrefix()
                        success("Du wurdest in die Warteschlange für den Event Server eingereiht.")
                    }
                } else {
                    player.sendText {
                        appendErrorPrefix()
                        error("Du bist bereits in einer Warteschlange!")
                    }
                }
            }
        }
}
