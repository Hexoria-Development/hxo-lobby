package de.hiorcraft.nex.nexLobby.utils

import dev.slne.surf.surfapi.bukkit.api.permission.PermissionRegistry

object PermissionRegistry : PermissionRegistry() {

    private const val PREFIX = "nex.lobby"
    private const val COMMAND_PREFIX = "$PREFIX.command"

    val COMMAND_SPAWN = create("$COMMAND_PREFIX.spawn")
    val COMMAND_LOBBY = create("$COMMAND_PREFIX.lobby")

    val EVENT_BYPASS = create("$PREFIX.event.bypass")
    val PROTECTION_BYPASS = create("$PREFIX.bypass")


}