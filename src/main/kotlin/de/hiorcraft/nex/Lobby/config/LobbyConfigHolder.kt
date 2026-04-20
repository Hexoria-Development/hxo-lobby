package de.hiorcraft.nex.Lobby.config

import de.hiorcraft.nex.Lobby.plugin
import dev.slne.surf.surfapi.core.api.config.manager.SpongeConfigManager
import dev.slne.surf.surfapi.core.api.config.surfConfigApi

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