package de.hiorcraft.nex.Lobby.hook.npc

import de.hiorcraft.nex.Lobby.eventServerAccess
import de.hiorcraft.nex.Lobby.manager.EventQueueManager
import de.hiorcraft.nex.Lobby.plugin
import dev.hiorcraft.nex.base.api.common.state.EventServerState
import de.hiorcraft.nex.Lobby.utils.Locations
import de.hiorcraft.nex.Lobby.utils.PermissionRegistry
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.api.paper.SurfApiPaper
import dev.slne.surf.npc.api.dsl.npc
import dev.slne.surf.npc.api.event.NpcInteractEvent
import dev.slne.surf.npc.api.npc.Npc
import dev.slne.surf.npc.api.npc.rotation.NpcRotationType
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.EntityType

object SurfNpcHook {
    lateinit var eventNPC: Npc
    lateinit var shopNPC: Npc
    lateinit var smashNPC: Npc
    lateinit var uhcNPC: Npc
    lateinit var ribNPC: Npc
    lateinit var rubineNPC: Npc
    lateinit var unknownNPC: Npc

    fun initialize() {
        EventQueueManager.initialize { SurfApiPaper.sendPlayerToServer(this, it) }
        createEventNpc()
        createShopNPc()
        createSmashNPC()
        createUhcNPC()
        createIrbNpc()
        createRubineNPC()
        createUnknowNPC()

        plugin.logger.info("Successfully loaded surf-npc integration.")
    }

    private fun createEventNpc() {
        eventNPC = npc {
            displayName = {
                warning("Event".toSmallCaps(), TextDecoration.BOLD)
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
                        player.sendText {
                            appendSuccessPrefix()
                            success("Du wirst zum Event-Server verbunden...")
                        }
                        SurfApiPaper.sendPlayerToServer(player, "event01")
                    }

                    EventServerState.WATING -> {
                        if (player.hasPermission(PermissionRegistry.EVENT_QUEUE_BYPASS)) {
                            player.sendText {
                                appendSuccessPrefix()
                                success("Du wirst zum Event-Server verbunden...")
                            }
                            SurfApiPaper.sendPlayerToServer(player, "event01")
                        } else {
                            EventQueueManager.addToQueue(player)
                        }
                    }

                    EventServerState.CLOSED, EventServerState.UNKNOWN -> {
                        if (player.hasPermission(PermissionRegistry.EVENT_QUEUE_BYPASS)) {
                            player.sendText {
                                appendSuccessPrefix()
                                success("Du wirst zum Event-Server verbunden...")
                            }
                            SurfApiPaper.sendPlayerToServer(player, "event01")
                        } else {
                            player.sendText {
                                appendErrorPrefix()
                                error("Aktuell läuft kein Event.")
                            }
                        }
                    }
                }

            }
        }
    }

    private fun createShopNPc() {
        shopNPC = npc {
            displayName = {
                note("Shop".toSmallCaps(), TextDecoration.BOLD)
            }
            type = EntityType.MANNEQUIN
            uniqueName = "shop_npc"
            skin = SurfNpcSkins.SHOP.getSkin()

            location = Locations.SHOP_NPC.getLocation()

            rotationType = NpcRotationType.PER_PLAYER

            withEventHandler<NpcInteractEvent> {
                it.player.sendText {
                    appendInfoPrefix()
                    error("unknown")
                }
            }
        }
    }

    private fun createSmashNPC() {
        smashNPC = npc {
            displayName = {
                warning("smash".toSmallCaps(), TextDecoration.BOLD)
                appendNewline()
                error("Server - %unknown%")
                appendNewline()
                secondary("» Bald verfügbar «")
            }
            type = EntityType.MANNEQUIN
            uniqueName = "smash"
            skin = SurfNpcSkins.UNKNOWN.getSkin()

            location = Locations.SMASH_NPC.getLocation()
            rotationType = NpcRotationType.PER_PLAYER
        }
    }

    private fun createIrbNpc() {
        ribNPC = npc {
            displayName = {
                warning("Random Item Battle".toSmallCaps(), TextDecoration.BOLD)
                appendNewline()
                error("Server - %unknown%")
                appendNewline()
                secondary("» Bald verfügbar «")
            }
            type = EntityType.MANNEQUIN
            uniqueName = "rib"
            skin = SurfNpcSkins.UNKNOWN.getSkin()

            location = Locations.RIB_NPC.getLocation()
            rotationType = NpcRotationType.PER_PLAYER
        }
    }

    private fun createUhcNPC() {
        uhcNPC = npc {
            displayName = {
                warning("uhc".toSmallCaps(), TextDecoration.BOLD, TextDecoration.OBFUSCATED)
            }
            type = EntityType.MANNEQUIN
            uniqueName = "uhc"
            skin = SurfNpcSkins.UNKNOWN.getSkin()

            location = Locations.UHC_NPC.getLocation()
            rotationType = NpcRotationType.PER_PLAYER
        }
    }

    private fun createRubineNPC() {
        rubineNPC = npc {
            displayName = {
                note("rubine".toSmallCaps(), TextDecoration.BOLD, TextDecoration.OBFUSCATED)
            }
            type = EntityType.MANNEQUIN
            uniqueName = "rubine"
            skin = SurfNpcSkins.RUBINE.getSkin()

            location = Locations.RUBINE_NPC.getLocation()
            rotationType = NpcRotationType.PER_PLAYER

            withEventHandler<NpcInteractEvent> {
                it.player.sendText {
                    appendInfoPrefix()
                    info("Du hast bereits deine tägliche Belohnung erhalten.")
                    appendSpace()
                    error("Die nächste ist in %data%")
                }
            }
        }
    }

    private fun createUnknowNPC() {
        unknownNPC = npc {
            displayName = {
                warning("unknown".toSmallCaps(), TextDecoration.BOLD, TextDecoration.OBFUSCATED)
            }
            type = EntityType.MANNEQUIN
            uniqueName = "unknown"
            skin = SurfNpcSkins.UNKNOWN.getSkin()

            location = Locations.UNKNOWN_NPC.getLocation()
            rotationType = NpcRotationType.PER_PLAYER
        }
    }
}
