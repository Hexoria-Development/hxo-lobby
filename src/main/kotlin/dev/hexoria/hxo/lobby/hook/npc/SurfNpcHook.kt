package dev.hexoria.hxo.lobby.hook.npc

import dev.hexoria.hxo.lobby.plugin
import dev.hexoria.hxo.lobby.utils.Locations
import dev.hexoria.hxo.lobby.redisLoader
import dev.hexoria.hxo.lobby.BINGO_SERVERS
import dev.slne.surf.api.core.messages.adventure.buildText
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.npc.api.dsl.npc
import dev.slne.surf.npc.api.event.NpcInteractEvent
import dev.slne.surf.npc.api.npc.Npc
import dev.slne.surf.npc.api.npc.rotation.NpcRotationType
import dev.slne.surf.core.api.paper.util.surfPlayer
import dev.slne.surf.core.api.common.SurfCoreApi
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import com.github.shynixn.mccoroutine.folia.launch

object SurfNpcHook {

    var eventServerDisplayName: String = "Event"

    lateinit var eventNPC: Npc
    lateinit var shopNPC: Npc
    lateinit var smashNPC: Npc
    lateinit var bingoNPC: Npc
    lateinit var ribNPC: Npc
    lateinit var rubineNPC: Npc
    lateinit var MurderNPC: Npc

    private var lastBingoStatus = mapOf<String, String>()

    fun initialize() {
        createEventNpc()
        createShopNPc()
        createSmashNPC()
        createBingNPC()
        createIrbNpc()
        createRubineNPC()
        createMurderNPC()

        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            if (!isBingoNpcInitialized()) return@Runnable

            val currentStatus = BINGO_SERVERS.associateWith { serverName ->
                redisLoader.bingoServerStates[serverName]?.get() ?: "OFFLINE"
            }

            if (currentStatus == lastBingoStatus) return@Runnable
            lastBingoStatus = currentStatus

            val component = buildText {
                warning("Bingo".toSmallCaps(), TextDecoration.BOLD)
                appendNewline()
                appendNewline()
                currentStatus.forEach { (serverName, status) ->
                    when (status) {
                        "WAITING" -> success("$serverName: Wartet auf Spieler")
                        "RUNNING" -> error("$serverName: Runde läuft")
                        else      -> secondary("$serverName: Offline")
                    }
                    appendNewline()
                }
            }
            bingoNPC.setDisplayName(component)
            bingoNPC.refresh()
        }, 20L, 20L * 5L)

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
                it.player.sendText {
                    appendErrorPrefix()
                    error("Aktuell findet kein Event statt!")
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

//    private fun createBingNPC() {
//        bingoNPC = npc {
//            displayName = {
//                useTransparentNametagBackground = true
//                warning("Bingo".toSmallCaps(), TextDecoration.BOLD)
//                appendNewline()
//                appendNewline()
//                success("» Neue «")
//                appendNewline()
//            }
//            type = EntityType.MANNEQUIN
//            uniqueName = "bingo_npc"
//            skin = SurfNpcSkins.BINGO.getSkin()
//
//            location = Locations.UHC_NPC.getLocation()
//            rotationType = NpcRotationType.PER_PLAYER
//        }
//    }

    private fun createBingNPC() {
        bingoNPC = npc {
            displayName = {
                useTransparentNametagBackground = true
                warning("Bingo".toSmallCaps(), TextDecoration.BOLD)
                appendNewline()
                appendNewline()
                secondary("Lade...")
                appendNewline()
            }
            type = EntityType.MANNEQUIN
            uniqueName = "bingo_npc"
            skin = SurfNpcSkins.BINGO.getSkin()

            location = Locations.UHC_NPC.getLocation()
            rotationType = NpcRotationType.PER_PLAYER

            withEventHandler<NpcInteractEvent> {
                handleBingoNpcClick(it.player)
            }
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
    fun isBingoNpcInitialized(): Boolean = ::bingoNPC.isInitialized
}

private fun handleBingoNpcClick(player: Player) {
    val states = redisLoader.bingoServerStates

    // Status-Übersicht im Chat zeigen
    player.sendText {
        appendNewline()
        note("  Bingo Server".toSmallCaps(), TextDecoration.BOLD)
        appendNewline()
        BINGO_SERVERS.forEach { serverName ->
            val status = states[serverName]?.get() ?: "OFFLINE"
            appendNewline()
            secondary("  $serverName ")
            darkSpacer("│")
            appendSpace()
            when (status) {
                "WAITING" -> success("Wartet auf Spieler")
                "RUNNING" -> error("Runde läuft")
                else      -> warning("Offline")
            }
        }
        appendNewline()
    }

    // Ersten freien Server finden und Spieler einreihen
    val waitingServer = BINGO_SERVERS.firstOrNull { serverName ->
        states[serverName]?.get() == "WAITING"
    }

    if (waitingServer == null) {
        player.sendText {
            appendErrorPrefix()
            error("Aktuell sind alle Bingo-Server belegt oder offline!")
        }
        return
    }

    queueToBingoServer(player, waitingServer)
}

private fun queueToBingoServer(player: Player, serverName: String) {
    val server = SurfCoreApi.getServerByName(serverName) ?: run {
        player.sendText {
            appendErrorPrefix()
            error("Bingo-Server '$serverName' nicht gefunden!")
        }
        return
    }

    plugin.launch {
        val status = SurfCoreApi.sendPlayerAwaiting(player.surfPlayer, server)

        if (status.isSuccessful()) {
            player.sendText {
                appendSuccessPrefix()
                success("Du wirst zu $serverName weitergeleitet...")
            }
        } else {
            player.sendText {
                appendErrorPrefix()
                error("Verbindung zu $serverName fehlgeschlagen: ${status.status}")
            }
        }
    }
}


