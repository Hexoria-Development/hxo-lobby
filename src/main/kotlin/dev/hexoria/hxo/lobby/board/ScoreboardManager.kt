package dev.hexoria.hxo.lobby.board

import dev.hexoria.hxo.lobby.hook.luckperms.LuckPermsHook
import dev.slne.surf.api.paper.scoreboard.SurfAutoUpdatableScoreboard
import dev.slne.surf.api.paper.scoreboard.SurfScoreboardApi
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player

fun HxoScoreboard(player: Player): SurfAutoUpdatableScoreboard {
    val title = MiniMessage.miniMessage().deserialize("<b><gradient:#55FF55:#1a5c1a>Hexoria</gradient></b>")
    val luckPermsEnabled = Bukkit.getPluginManager().isPluginEnabled("LuckPerms")

    return SurfScoreboardApi.createScoreboard(title)
        .maxLines(15)
        .addEmptyLine()
        .addUpdatableLine {
            if (luckPermsEnabled) LuckPermsHook.getPrefixBitmapOnly(player)
            else Component.text("\uE003")
        }
        .addUpdatableLine {
            val expiry = if (luckPermsEnabled) LuckPermsHook.getPrefixDurationFormatted(player) else "Permanent"
            Component.text(expiry)
        }
        .addEmptyLine()
        .addLine(Component.text("\uE00A"))
        .addLine(Component.text("0"))
        .addEmptyLine()
        .addLine(Component.text("\uE009"))
        .addLine(Component.text("Soon"))
        .buildAutoUpdatable()
}