package dev.hexoria.hxo.lobby.command

import dev.hexoria.hxo.lobby.lobbyConfigHolder
import dev.hexoria.hxo.lobby.utils.PermissionRegistry
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