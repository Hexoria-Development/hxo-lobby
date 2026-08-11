package dev.hexoria.hxo.lobby

import dev.hexoria.hxo.lobby.command.discordCommand
import dev.hexoria.hxo.lobby.command.lobbyCommand
import dev.hexoria.hxo.lobby.command.scoreboardCommand
import dev.hexoria.hxo.lobby.command.spawnCommand
import dev.hexoria.hxo.lobby.config.LobbyConfigHolder
import dev.hexoria.hxo.lobby.hook.npc.SurfNpcHook
import dev.hexoria.hxo.lobby.listener.*
import dev.hexoria.hxo.lobby.manager.PushbackManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)
lateinit var lobbyConfigHolder: LobbyConfigHolder

class PaperMain : JavaPlugin() {

    override fun onLoad() {
        // NavigatorInventory und KosmetikInventory entfernt
    }

    override fun onEnable() {
        logger.info("is starting.....")
        redisLoader.connect()
        lobbyConfigHolder = LobbyConfigHolder()

        if (lobbyConfigHolder.lobbyConfig.enablednpc == true && Bukkit.getPluginManager().isPluginEnabled("surf-npc-paper")) {
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
        scoreboardCommand()
        discordCommand()
        logger.info("Commands registered")
    }

    override fun onDisable() {
        logger.info("is disabled....")
        redisLoader.disconnect()
        logger.info("Bye :)")
    }
}