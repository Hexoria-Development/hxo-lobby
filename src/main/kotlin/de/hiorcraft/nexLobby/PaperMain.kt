package de.hiorcraft.nexLobby

import de.hiorcraft.nexLobby.listener.DamageListener
import de.hiorcraft.nexLobby.listener.EntitySpawnListener
import de.hiorcraft.nexLobby.listener.FoodListener
import de.hiorcraft.nexLobby.listener.WorldProtectionListener
import de.hiorcraft.nexLobby.listener.PlayerConnectionListener
import de.hiorcraft.nexLobby.listener.ItemInteractListener
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : JavaPlugin() {

    override fun onEnable() {

        logger.info("is starting.....")

        val manager = server.pluginManager

        manager.registerEvents(DamageListener(), this)
        manager.registerEvents(FoodListener(), this)
        manager.registerEvents(WorldProtectionListener(), this)
        manager.registerEvents(EntitySpawnListener, this)
        manager.registerEvents(PlayerConnectionListener, this)
        manager.registerEvents(ItemInteractListener, this)

        logger.info("Listener registered")
    }

    override fun onDisable() {
    }
}
