package dev.hexoria.hxo.lobby.command

import dev.hexoria.hxo.lobby.listener.PlayerConnectionListener
import dev.hexoria.hxo.lobby.utils.PermissionRegistry
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.api.core.messages.adventure.sendText

fun scoreboardCommand() = commandTree("HxoScoreboard") {
    withPermission(PermissionRegistry.COMMAND_SCOREBOARD)

    literalArgument("reload") {
        playerExecutor { player, _ ->
            PlayerConnectionListener.reload(player)
            player.sendText {
                appendInfoPrefix()
                info("Scoreboard wurde neu geladen.")
            }
        }
    }
}