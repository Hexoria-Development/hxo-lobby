package de.hiorcraft.nex.Lobby.manager

import de.hiorcraft.nex.Lobby.eventServerAccess
import de.hiorcraft.nex.Lobby.plugin
import dev.hiorcraft.nex.base.api.common.state.EventServerState
import dev.slne.surf.api.core.messages.adventure.sendText
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

object EventQueueManager {
    private val queue = ArrayDeque<UUID>()
    private var lastState = EventServerState.CLOSED
    private lateinit var sendPlayerToServer: Player.(String) -> Unit

    fun initialize(sendPlayerToServer: Player.(String) -> Unit) {
        this.sendPlayerToServer = sendPlayerToServer
        lastState = eventServerAccess.getEventServerState()
        startMonitor()
    }

    fun addToQueue(player: Player) {
        if (queue.contains(player.uniqueId)) {
            player.sendText {
                appendWarningPrefix()
                warning("Du bist bereits in der Queue (Position ${queue.indexOf(player.uniqueId) + 1}).")
            }
            return
        }
        queue.addLast(player.uniqueId)
        player.sendText {
            appendInfoPrefix()
            info("Du wurdest zur Queue hinzugefügt (Position ${queue.size}).")
        }
    }

    fun removeFromQueue(uuid: UUID) {
        queue.remove(uuid)
    }

    private fun startMonitor() {
        Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, {
            val currentState = eventServerAccess.getEventServerState()

            if (lastState == EventServerState.WATING && currentState == EventServerState.OPEN) {
                drainQueue()
            }

            lastState = currentState
        }, 20L, 20L)
    }

    private fun drainQueue() {
        Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, { task ->
            if (queue.isEmpty()) {
                task.cancel()
                return@runAtFixedRate
            }

            val uuid = queue.removeFirst()
            val player = Bukkit.getPlayer(uuid)

            if (player != null) {
                player.sendText {
                    appendSuccessPrefix()
                    success("Du wirst jetzt zum Event-Server verbunden...")
                }
                sendPlayerToServer(player, "event01")
            }
        }, 1L, 20L)
    }
}