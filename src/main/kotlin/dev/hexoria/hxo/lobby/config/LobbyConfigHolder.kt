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

    val lobbyConfig get() = configManager.config
}