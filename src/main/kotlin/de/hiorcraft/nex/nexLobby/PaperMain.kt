package de.hiorcraft.nex.nexLobby

import de.hiorcraft.nex.nexLobby.DoubleJump.DoubleJump
import de.hiorcraft.nex.nexLobby.listener.DamageListener
import de.hiorcraft.nex.nexLobby.listener.FoodListener
import de.hiorcraft.nex.nexLobby.listener.WorldProtectionListener
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : JavaPlugin() {

    override fun onEnable() {

        logger.info("is starting.....")

        val manager = server.pluginManager

        manager.registerEvents(DamageListener(), this)
        manager.registerEvents(FoodListener(), this)
        manager.registerEvents(WorldProtectionListener(), this)
        manager.registerEvents(DoubleJump(), this)

        logger.info("Listener registered")
    }

    override fun onDisable() {
    }
}
