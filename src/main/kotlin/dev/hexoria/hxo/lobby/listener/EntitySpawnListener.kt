package dev.hexoria.hxo.lobby.listener

import org.bukkit.entity.Mob
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent

object EntitySpawnListener : Listener {

    @EventHandler
    fun onSpawn(event: CreatureSpawnEvent) {
        // Von Plugins/Commands erzeugte Entities (NPCs, Nametags, etc.) zulassen
        when (event.spawnReason) {
            CreatureSpawnEvent.SpawnReason.CUSTOM,
            CreatureSpawnEvent.SpawnReason.COMMAND,
            CreatureSpawnEvent.SpawnReason.SPAWNER_EGG -> return
            else -> {}
        }

        // Nur echte Mobs (Tiere/Monster) blockieren.
        // ArmorStands, Display-Entities (Nametags/Holograms) sind keine Mobs und werden nicht angefasst.
        if (event.entity is Mob) {
            event.isCancelled = true
        }
    }
}
