package dev.hexoria.hxo.lobby.manager

import dev.hexoria.hxo.lobby.lobbyConfigHolder
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType

object SubLobbyManager {

    fun loadWorlds() {
        lobbyConfigHolder.lobbyConfig.subLobbies.forEach { subLobby ->
            if (Bukkit.getWorld(subLobby.world) == null) {
                createWorld(subLobby.world)
            }
        }
    }

    fun createWorld(worldName: String): World? = WorldCreator(worldName)
        .generator(VoidGenerator())
        .type(WorldType.FLAT)
        .environment(World.Environment.NORMAL)
        .createWorld()
}
