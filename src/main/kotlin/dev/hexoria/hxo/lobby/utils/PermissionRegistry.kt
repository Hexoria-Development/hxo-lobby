package dev.hexoria.hxo.lobby.utils

import dev.slne.surf.api.paper.permission.PermissionRegistry

object PermissionRegistry : PermissionRegistry() {

    private const val PREFIX = "hxo.lobby"
    private const val COMMAND_PREFIX = "$PREFIX.command"

    val COMMAND_SPAWN = create("$COMMAND_PREFIX.spawn")
    val COMMAND_LOBBY_RELOAD = create("$COMMAND_PREFIX.lobby.reload")

    val COMMAND_SUBLOBBY = create("$COMMAND_PREFIX.subdraw")
    val COMMAND_SUBLOBBY_CREATE = create("$COMMAND_PREFIX.subloby.onCreate")
    val COMMAND_SUBLOBB_DELETE = create("$COMMAND_PREFIX.subloby.delete")
    val COMMAND_SUBLOBBY_JOIN = create("$COMMAND_PREFIX.subloby.join")
    val COMMAND_SUBLOBBY_LIST = create("$COMMAND_PREFIX.subloby.list")
    val COMMAND_SUBLOBBY_EDIT = create("$COMMAND_PREFIX.subloby.edit")

    val PUSHBACK_ATTACK = create("$PREFIX.pushback.attack")
    val PUSHBACK_ITEM = create("$PREFIX.pushback.item")

    val ELYTRA_BOOST = create("$PREFIX.elytraboost")

    val EVENT_QUEUE_BYPASS = create("$PREFIX.event.queue.bypass")

}