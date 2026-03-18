package de.hiorcraft.nex.Lobby.command

import de.hiorcraft.nex.Lobby.config.LobbyConfigHolder
import de.hiorcraft.nex.Lobby.utils.PermissionRegistry
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText

fun lobbyCommand() = commandTree("surflobby") {
    withPermission(PermissionRegistry.COMMAND_LOBBY)

    literalArgument("reload") {
        withPermission(PermissionRegistry.COMMAND_LOBBY_RELOAD)
        anyExecutor { executor, _ ->
            LobbyConfigHolder.reload()

            executor.sendText {
                appendSuccessPrefix()
                success("Lobby configuration reloaded successfully.")
            }
        }
    }
}