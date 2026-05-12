package de.hiorcraft.nex.Lobby.hook.npc

import de.hiorcraft.nex.Lobby.plugin
import de.hiorcraft.nex.Lobby.utils.Locations
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.sendText
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
                note("Event".toSmallCaps(), TextDecoration.BOLD)
            }
            type = EntityType.MANNEQUIN
            uniqueName = "event_npc"
            skin = SurfNpcSkins.EVENT.getSkin()

            location = Locations.EVENT_NPC.getLocation()

            rotationType = NpcRotationType.PER_PLAYER

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
                    info("Der Shop würd noch gebaut.")
                }
            }
        }
    }

    private fun createSmashNPC() {
        smashNPC = npc {
            displayName = {
                note("smash".toSmallCaps(), TextDecoration.BOLD, TextDecoration.OBFUSCATED)
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
                note("rib".toSmallCaps(), TextDecoration.BOLD, TextDecoration.OBFUSCATED)
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
                note("uhc".toSmallCaps(), TextDecoration.BOLD, TextDecoration.OBFUSCATED)
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
                    primary("Bald kannst du deine Tägliche Belohnung hier abholen!")
                }
            }
        }
    }

    private fun createUnknowNPC() {
        unknownNPC = npc {
            displayName = {
                note("unknown".toSmallCaps(), TextDecoration.BOLD, TextDecoration.OBFUSCATED)
            }
            type = EntityType.MANNEQUIN
            uniqueName = "unknown"
            skin = SurfNpcSkins.UNKNOWN.getSkin()

            location = Locations.UNKNOWN_NPC.getLocation()
            rotationType = NpcRotationType.PER_PLAYER
        }
    }
}
