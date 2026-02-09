package de.hiorcraft.nex.nexLobby

import org.bukkit.plugin.java.JavaPlugin

var plugin: PaperMain get() = JavaPlugin.getPlugin(PaperMain::class.java)
    set(value) {
    }

class PaperMain : JavaPlugin() {

    override fun onEnable() {
    }

    override fun onDisable() {
    }
}
