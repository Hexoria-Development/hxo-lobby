package de.hiorcraft.nex.Lobby.utils

import dev.slne.surf.api.paper.permission.PermissionRegistry

object PermissionRegistry : PermissionRegistry() {

    private const val PREFIX = "nex.lobby"
    private const val COMMAND_PREFIX = "$PREFIX.command"

    val COMMAND_SPAWN = create("$COMMAND_PREFIX.spawn")
    val COMMAND_LOBBY_RELOAD = create("$COMMAND_PREFIX.lobby.reload")

    val PUSHBACK_ATTACK = create("$PREFIX.pushback.attack")
    val PUSHBACK_ITEM = create("$PREFIX.pushback.item")

    val ELYTRA_BOOST = create("$PREFIX.elytraboost")

}