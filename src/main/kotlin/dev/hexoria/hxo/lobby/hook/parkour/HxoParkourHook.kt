package dev.hexoria.hxo.lobby.hook.parkour

import dev.hiorcraft.nex.parkour.api.NexParkourAPI
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object HxoParkourHook {

    private val api: NexParkourAPI?
        get() = Bukkit.getServicesManager().load(NexParkourAPI::class.java)

    fun isRunning(player: Player): Boolean = api?.isRunning(player.uniqueId) ?: false

}