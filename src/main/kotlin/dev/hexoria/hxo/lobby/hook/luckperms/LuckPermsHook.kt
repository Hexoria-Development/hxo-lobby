package dev.hexoria.hxo.lobby.hook.luckperms

import net.kyori.adventure.text.Component
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.node.NodeType
import org.bukkit.entity.Player
import java.time.Duration
import java.time.Instant

object LuckPermsHook {

    private val luckPerms by lazy { LuckPermsProvider.get() }

    fun getPrefixBitmapOnly(player: Player): Component {
        val user = luckPerms.userManager.getUser(player.uniqueId) ?: return Component.text("\uE003")
        val prefix = user.cachedData.metaData.prefix ?: return Component.text("\uE003")

        val bitmap = prefix.trim().split(" ").first()

        return Component.text(bitmap)
    }

    fun getPrefixExpiry(player: Player): Instant? {
        val user = luckPerms.userManager.getUser(player.uniqueId) ?: return null
        val primaryGroup = user.primaryGroup

        return user.getNodes(NodeType.INHERITANCE)
            .firstOrNull { it.groupName.equals(primaryGroup, ignoreCase = true) }
            ?.takeIf { it.hasExpiry() }
            ?.expiry
    }

    fun getPrefixDurationFormatted(player: Player): String {
        val expiry = getPrefixExpiry(player) ?: return "Permanent"
        val duration = Duration.between(Instant.now(), expiry)

        if (duration.isNegative) return "Abgelaufen"

        val days = duration.toDays()
        val hours = duration.toHours() % 24
        val minutes = duration.toMinutes() % 60

        return buildString {
            if (days > 0) append("${days}d ")
            if (hours > 0) append("${hours}h ")
            append("${minutes}m")
        }.trim()
    }
}