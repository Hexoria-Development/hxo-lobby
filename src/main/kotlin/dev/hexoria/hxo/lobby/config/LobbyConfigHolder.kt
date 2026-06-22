package dev.hexoria.hxo.lobby.config

import dev.hexoria.hxo.lobby.plugin
import dev.slne.surf.api.core.config.manager.SpongeConfigManager
import dev.slne.surf.api.core.config.surfConfigApi

class LobbyConfigHolder {
    private val configManager: SpongeConfigManager<LobbyConfig>

    init {
        surfConfigApi.createSpongeYmlConfig(
            LobbyConfig::class.java,
            plugin.dataPath,
            "lobby.yml"
        )
        configManager = surfConfigApi.getSpongeConfigManagerForConfig(
            LobbyConfig::class.java
        )
        reload()
    }

    fun reload() {
        configManager.reloadFromFile()
    }

    fun addSubLobby(subLobby: LobbyConfig.SubLobbyConfig) {
        configManager.config = configManager.config.copy(
            subLobbies = configManager.config.subLobbies + subLobby
        )
        configManager.save()
    }

    fun removeSubLobby(name: String) {
        configManager.config = configManager.config.copy(
            subLobbies = configManager.config.subLobbies.filterNot { it.name.equals(name, ignoreCase = true) }
        )
        configManager.save()
    }

    fun updateSubLobbySpawn(name: String, spawnPoint: LobbyConfig.LocationConfig) {
        configManager.config = configManager.config.copy(
            subLobbies = configManager.config.subLobbies.map {
                if (it.name.equals(name, ignoreCase = true)) it.copy(spawnPoint = spawnPoint) else it
            }
        )
        configManager.save()
    }

    val lobbyConfig get() = configManager.config
}