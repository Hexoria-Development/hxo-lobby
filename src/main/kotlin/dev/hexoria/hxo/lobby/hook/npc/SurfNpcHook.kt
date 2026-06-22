package dev.hexoria.hxo.lobby.hook.npc

import dev.hexoria.hxo.lobby.eventServerAccess
import dev.hexoria.hxo.lobby.plugin
import dev.hiorcraft.nex.base.api.common.state.EventServerState
import dev.hexoria.hxo.lobby.utils.Locations
import dev.hexoria.hxo.lobby.utils.PermissionRegistry
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
                useTransparentNametagBackground = true
                error("Event".toSmallCaps(), TextDecoration.BOLD)
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
                        player.sendText {
                            appendSuccessPrefix()
                            success("Du wirst mit dem Event-Server verbunden ...")
                        }
                        SurfApiPaper.sendPlayerToServer(player, "event01")
                    }

                    EventServerState.WATING -> {
                        if (player.hasPermission(PermissionRegistry.EVENT_QUEUE_BYPASS)) {
                            player.sendText {
                                appendSuccessPrefix()
                                success("Du wirst mit dem Event-Server verbunden ...")
                            }
                            SurfApiPaper.sendPlayerToServer(player, "event01")
                        }
                    }

                    EventServerState.CLOSED, EventServerState.UNKNOWN -> {
                        if (player.hasPermission(PermissionRegistry.EVENT_QUEUE_BYPASS)) {
                            player.sendText {
                                appendSuccessPrefix()
                                success("Du wirst mit dem Event-Server verbunden ...")
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

    private fun createUhcNPC() {
        uhcNPC = npc {
            displayName = {
                useTransparentNametagBackground = true
                warning("UNKNOWN".toSmallCaps(), TextDecoration.BOLD, TextDecoration.OBFUSCATED)
                appendNewline()
            }
            type = EntityType.MANNEQUIN
            uniqueName = "UHC_npc"
            skin = SurfNpcSkins.UNKNOWN.getSkin()

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

    private fun createUnknowNPC() {
        unknownNPC = npc {
            displayName = {
                useTransparentNametagBackground = true
                warning("unknown".toSmallCaps(), TextDecoration.BOLD, TextDecoration.OBFUSCATED)
                appendNewline()
            }
            type = EntityType.MANNEQUIN
            uniqueName = "unknown_npc"
            skin = SurfNpcSkins.UNKNOWN.getSkin()

            location = Locations.UNKNOWN_NPC.getLocation()
            rotationType = NpcRotationType.PER_PLAYER
        }
    }
}
