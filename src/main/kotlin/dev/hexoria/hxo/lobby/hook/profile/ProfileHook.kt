package dev.hexoria.hxo.lobby.hook.profile

import dev.hexoria.hxo.profile.api.hxoProfileApi
import org.bukkit.entity.Player

object ProfileHook {
    fun openMenu(player: Player) {
        hxoProfileApi.openOwnProfileMenu(player.uniqueId)
    }
}