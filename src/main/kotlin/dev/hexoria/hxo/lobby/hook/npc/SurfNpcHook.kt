package dev.hexoria.hxo.lobby.hook.npc

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.launch
import dev.hexoria.hxo.base.api.common.state.EventServerState
import dev.hexoria.hxo.lobby.eventServerAccess
import dev.hexoria.hxo.lobby.plugin
import dev.hexoria.hxo.lobby.utils.Locations
import dev.hexoria.hxo.lobby.utils.PermissionRegistry
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.npc.api.dsl.npc
import dev.slne.surf.npc.api.event.NpcInteractEvent
import dev.slne.surf.npc.api.npc.Npc
import dev.slne.surf.npc.api.npc.rotation.NpcRotationType
import dev.slne.surf.queue.api.SurfQueue
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import java.util.UUID

object SurfNpcHook {
    private const val MAX_QUEUE_PRIORITY = 127

    private val eventQueue by lazy { SurfQueue.byServer("event01") }

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
                error("Event".toSmallCaps(), TextDecoration.BOLD)
                appendNewline()
                appendNewline()
                secondary("Event - 26.1.2")
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
                    }

                    EventServerState.WATING -> {
                        queueToEventServer(player, bypassPriority = player.hasPermission(PermissionRegistry.EVENT_QUEUE_BYPASS))
                    }

                    EventServerState.CLOSED -> {
                        if (player.hasPermission(PermissionRegistry.EVENT_QUEUE_BYPASS)) {
                            queueToEventServer(player, bypassPriority = true)
                        } else {
                            player.sendText {
                                appendErrorPrefix()
                                error("Der Event Server ist aktuell geschlossen!")
                            }
                        }
                    }

                    EventServerState.UNKNOWN -> {
                        player.sendText {
                            appendErrorPrefix()
                            error("Aktuell findet kein Event statt!")
                        }
                    }
                }
            }
        }
    }

    fun removeFromEventQueue(uuid: UUID) {
        plugin.launch {
            eventQueue.dequeue(uuid)
        }
    }

    private fun queueToEventServer(player: Player, bypassPriority: Boolean = false) {
        plugin.launch(plugin.entityDispatcher(player)) {
            val added = if (bypassPriority) {
                eventQueue.enqueue(player.uniqueId, MAX_QUEUE_PRIORITY)
            } else {
                eventQueue.enqueue(player.uniqueId)
            }

            if (added) {
                player.sendText {
                    appendInfoPrefix()
                    info("Du wurdest in die Event-Server-Warteschlange eingereiht.")
                }
            } else {
                val position = eventQueue.getPosition(player.uniqueId)
                player.sendText {
                    appendWarningPrefix()
                    warning("Du befindest dich bereits in der Warteschlange${position?.let { " (Position ${it + 1})" } ?: ""}.")
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
}
