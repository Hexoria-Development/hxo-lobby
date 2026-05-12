package de.hiorcraft.nex.Lobby.command

import de.hiorcraft.nex.Lobby.lobbyConfigHolder
import de.hiorcraft.nex.Lobby.utils.PermissionRegistry
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.api.core.messages.adventure.sendText

fun spawnCommand() = commandTree("spawn") {
    withPermission(PermissionRegistry.COMMAND_SPAWN)
    playerExecutor { player, _ ->
        player.teleportAsync(lobbyConfigHolder.lobbyConfig.spawnPoint.toLocation()).thenRun {
            player.sendText {
                appendSuccessPrefix()
                success("Du wurdest zum Spawn teleportiert.")
            }
        }
    }
}