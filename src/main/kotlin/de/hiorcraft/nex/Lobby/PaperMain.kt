package de.hiorcraft.nex.Lobby

import de.hiorcraft.nex.Lobby.command.lobbyCommand
import de.hiorcraft.nex.Lobby.command.spawnCommand
import de.hiorcraft.nex.Lobby.config.LobbyConfigHolder
import de.hiorcraft.nex.Lobby.hook.npc.SurfNpcHook
import de.hiorcraft.nex.Lobby.listener.DamageListener
import de.hiorcraft.nex.Lobby.listener.DoubleJumpListener
import de.hiorcraft.nex.Lobby.listener.EntitySpawnListener
import de.hiorcraft.nex.Lobby.listener.FoodListener
import de.hiorcraft.nex.Lobby.listener.InventoryInteractListener
import de.hiorcraft.nex.Lobby.listener.ItemInteractListener
import de.hiorcraft.nex.Lobby.listener.PlayerConnectionListener
import de.hiorcraft.nex.Lobby.listener.PlayerMoveListener
import de.hiorcraft.nex.Lobby.listener.PushbackListener
import de.hiorcraft.nex.Lobby.listener.SpawnLocationListener
import de.hiorcraft.nex.Lobby.listener.WorldProtectionListener
import de.hiorcraft.nex.Lobby.listener.XpBarListener
import de.hiorcraft.nex.Lobby.manager.PushbackManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)
val surfNpcHook get() = Bukkit.getPluginManager().isPluginEnabled("surf-npc-paper")
lateinit var lobbyConfigHolder: LobbyConfigHolder

class PaperMain : JavaPlugin() {

    override fun onEnable() {

        logger.info("is starting.....")

        lobbyConfigHolder = LobbyConfigHolder()

        if (surfNpcHook) {
            SurfNpcHook.initialize()
        }

        PushbackManager.startTask()

        val manager = server.pluginManager

        manager.registerEvents(DamageListener(), this)
        manager.registerEvents(FoodListener(), this)
        manager.registerEvents(WorldProtectionListener(), this)
        manager.registerEvents(EntitySpawnListener, this)
        manager.registerEvents(SpawnLocationListener, this)
        manager.registerEvents(ItemInteractListener, this)
        manager.registerEvents(InventoryInteractListener, this)
        manager.registerEvents(PushbackListener(), this)
        manager.registerEvents(PlayerConnectionListener, this)
        manager.registerEvents(PlayerMoveListener, this)
        manager.registerEvents(XpBarListener, this)
        manager.registerEvents(DoubleJumpListener, this)

        logger.info("Listener registered")

        spawnCommand()
        lobbyCommand()
        logger.info("Commands registered")
    }

    override fun onDisable() {
        logger.info("is disabled....")

        logger.info("lobby config is disabled")

        logger.info("Bye :)")
    }
}

