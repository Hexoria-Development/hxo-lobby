package dev.hexoria.hxo.lobby.hook.profile

import dev.hexoria.hxo.profile.api.HxoProfileApi
import org.bukkit.entity.Player

object ProfileHook {
    fun openMenu(player: Player) {
        HxoProfileApi.openOwnProfileMenu(player.uniqueId)
    }
}