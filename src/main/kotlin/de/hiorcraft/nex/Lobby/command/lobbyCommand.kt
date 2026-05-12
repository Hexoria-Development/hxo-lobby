package de.hiorcraft.nex.Lobby.command

import de.hiorcraft.nex.Lobby.lobbyConfigHolder
import de.hiorcraft.nex.Lobby.utils.PermissionRegistry
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.slne.surf.api.core.messages.adventure.sendText

fun lobbyCommand() = commandTree("nexlobby") {
    withPermission(PermissionRegistry.COMMAND_LOBBY_RELOAD)

    literalArgument("reload") {
        anyExecutor { executor, _ ->
            lobbyConfigHolder.reload()

            executor.sendText {
                appendSuccessPrefix()
                success("Lobby configuration reloaded successfully.")
            }
        }
    }
}