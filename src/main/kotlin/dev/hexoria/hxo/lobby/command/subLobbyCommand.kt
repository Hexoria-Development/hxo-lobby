package dev.hexoria.hxo.lobby.command

import dev.hexoria.hxo.lobby.config.LobbyConfig
import dev.hexoria.hxo.lobby.lobbyConfigHolder
import dev.hexoria.hxo.lobby.manager.SubLobbyManager
import dev.hexoria.hxo.lobby.utils.PermissionRegistry
import dev.jorel.commandapi.kotlindsl.anyExecutor
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.stringArgument
import dev.slne.surf.api.core.messages.adventure.sendText
import org.bukkit.Bukkit

fun subLobbyCommand() = commandTree("subLobby") {
    withPermission(PermissionRegistry.COMMAND_SUBLOBBY)

    literalArgument("Create") {
        withPermission(PermissionRegistry.COMMAND_SUBLOBBY_CREATE)

        stringArgument("name") {
            playerExecutor { player, args ->
                val name = args["name"] as String

                if (lobbyConfigHolder.lobbyConfig.subLobbies.any { it.name.equals(name, ignoreCase = true) }) {
                    player.sendText {
                        appendErrorPrefix()
                        error("Es existiert bereits eine Sublobby mit dem Namen '$name'.")
                    }
                    return@playerExecutor
                }

                val worldName = "sublobby_$name"
                val world = SubLobbyManager.createWorld(worldName)

                if (world == null) {
                    player.sendText {
                        appendErrorPrefix()
                        error("Die Welt für die Sublobby '$name' konnte nicht erstellt werden.")
                    }
                    return@playerExecutor
                }

                world.setSpawnLocation(0, 100, 0)

                val spawnPoint = LobbyConfig.LocationConfig(
                    world = worldName,
                    x = 0.5,
                    y = 100.0,
                    z = 0.5,
                    yaw = 0.0f,
                    pitch = 0.0f
                )

                lobbyConfigHolder.addSubLobby(
                    LobbyConfig.SubLobbyConfig(
                        name = name,
                        world = worldName,
                        spawnPoint = spawnPoint
                    )
                )

                player.sendText {
                    appendSuccessPrefix()
                    success("Die Sublobby '$name' wurde erstellt.")
                }
            }
        }
    }

    literalArgument("Delete") {
        withPermission(PermissionRegistry.COMMAND_SUBLOBB_DELETE)

        stringArgument("name") {
            playerExecutor { player, args ->
                val name = args["name"] as String
                val subLobby = lobbyConfigHolder.lobbyConfig.subLobbies.find { it.name.equals(name, ignoreCase = true) }

                if (subLobby == null) {
                    player.sendText {
                        appendErrorPrefix()
                        error("Es existiert keine Sublobby mit dem Namen '$name'.")
                    }
                    return@playerExecutor
                }

                Bukkit.getWorld(subLobby.world)?.let { world ->
                    world.players.forEach { it.teleportAsync(lobbyConfigHolder.lobbyConfig.spawnPoint.toLocation()) }
                    Bukkit.unloadWorld(world, false)
                }

                lobbyConfigHolder.removeSubLobby(name)

                player.sendText {
                    appendSuccessPrefix()
                    success("Die Sublobby '$name' wurde gelöscht.")
                }
            }
        }
    }

    literalArgument("Join") {
        withPermission(PermissionRegistry.COMMAND_SUBLOBBY_JOIN)

        stringArgument("name") {
            playerExecutor { player, args ->
                val name = args["name"] as String
                val subLobby = lobbyConfigHolder.lobbyConfig.subLobbies.find { it.name.equals(name, ignoreCase = true) }

                if (subLobby == null) {
                    player.sendText {
                        appendErrorPrefix()
                        error("Es existiert keine Sublobby mit dem Namen '$name'.")
                    }
                    return@playerExecutor
                }

                player.teleportAsync(subLobby.spawnPoint.toLocation()).thenRun {
                    player.sendText {
                        appendSuccessPrefix()
                        success("Du wurdest zur Sublobby '$name' teleportiert.")
                    }
                }
            }
        }
    }

    literalArgument("List") {
        withPermission(PermissionRegistry.COMMAND_SUBLOBBY_LIST)

        anyExecutor { sender, _ ->
            val subLobbies = lobbyConfigHolder.lobbyConfig.subLobbies

            if (subLobbies.isEmpty()) {
                sender.sendText {
                    appendErrorPrefix()
                    error("Es existieren keine Sublobbys.")
                }
                return@anyExecutor
            }

            sender.sendText {
                appendInfoPrefix()
                info("Sublobbys: ")
                variableValue(subLobbies.joinToString(", ") { it.name })
            }
        }
    }

    literalArgument("Edit") {
        withPermission(PermissionRegistry.COMMAND_SUBLOBBY_EDIT)

        stringArgument("name") {
            literalArgument("Spawn") {
                playerExecutor { player, args ->
                    val name = args["name"] as String
                    val subLobby = lobbyConfigHolder.lobbyConfig.subLobbies.find { it.name.equals(name, ignoreCase = true) }

                    if (subLobby == null) {
                        player.sendText {
                            appendErrorPrefix()
                            error("Es existiert keine Sublobby mit dem Namen '$name'.")
                        }
                        return@playerExecutor
                    }

                    if (player.world.name != subLobby.world) {
                        player.sendText {
                            appendErrorPrefix()
                            error("Du musst dich in der Welt der Sublobby '$name' befinden.")
                        }
                        return@playerExecutor
                    }

                    val location = player.location
                    val spawnPoint = LobbyConfig.LocationConfig(
                        world = subLobby.world,
                        x = location.x,
                        y = location.y,
                        z = location.z,
                        yaw = location.yaw,
                        pitch = location.pitch
                    )

                    lobbyConfigHolder.updateSubLobbySpawn(name, spawnPoint)

                    player.sendText {
                        appendSuccessPrefix()
                        success("Der Spawnpunkt der Sublobby '$name' wurde aktualisiert.")
                    }
                }
            }
        }
    }
}
