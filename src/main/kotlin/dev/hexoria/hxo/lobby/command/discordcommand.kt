package dev.hexoria.hxo.lobby.command

import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.api.core.messages.adventure.sendText

fun discordCommand() = commandTree("discord") {
    withAliases("dc")

    playerExecutor { player, _ ->

        player.sendText {
            appendInfoPrefix()
            primary("Join our Discord server: ")
            variableValue("https://discord.com/invite/cU8tt3QsUp")
            clickOpensUrl("https://discord.com/invite/cU8tt3QsUp")
        }
    }
}