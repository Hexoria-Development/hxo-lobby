package de.hiorcraft.nex.Lobby.utils

import dev.slne.surf.surfapi.bukkit.api.permission.PermissionRegistry

object PermissionRegistry : PermissionRegistry() {

    private const val PREFIX = "nex.lobby"
    private const val COMMAND_PREFIX = "$PREFIX.command"

    val COMMAND_SPAWN = create("$COMMAND_PREFIX.spawn")
    val COMMAND_LOBBY = create("$COMMAND_PREFIX.lobby")
    val COMMAND_LOBBY_RELOAD = create("$COMMAND_PREFIX.lobby.reload")

}