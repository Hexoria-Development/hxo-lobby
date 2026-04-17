package de.hiorcraft.nex.Lobby

import de.hiorcraft.nex.Lobby.hook.npc.SurfNpcHook
import de.hiorcraft.nex.Lobby.listener.DamageListener
import de.hiorcraft.nex.Lobby.listener.EntitySpawnListener
import de.hiorcraft.nex.Lobby.listener.FoodListener
import de.hiorcraft.nex.Lobby.listener.WorldProtectionListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)
val surfNpcHook get() = Bukkit.getPluginManager().isPluginEnabled("surf-npc-paper")

class PaperMain : JavaPlugin() {

    override fun onEnable() {

        logger.info("is starting.....")


        if (surfNpcHook) {
            SurfNpcHook.initialize()
        }

        val manager = server.pluginManager

        manager.registerEvents(DamageListener(), this)
        manager.registerEvents(FoodListener(), this)
        manager.registerEvents(WorldProtectionListener(), this)
        manager.registerEvents(EntitySpawnListener, this)

        logger.info("Listener registered")


        logger.info("Commands registered")
    }

    override fun onDisable() {
    }
}
