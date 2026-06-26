package dev.hexoria.hxo.lobby.hook.parkour

import dev.hexoria.hxo.parkour.api.HxoParkourAPI
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object HxoParkourHook {

    private val api: HxoParkourAPI?
        get() = Bukkit.getServicesManager().load(HxoParkourAPI::class.java)

    fun isRunning(player: Player): Boolean = api?.isRunning(player.uniqueId) ?: false

}