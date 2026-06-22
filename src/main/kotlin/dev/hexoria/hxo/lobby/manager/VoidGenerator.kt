package dev.hexoria.hxo.lobby.manager

import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import java.util.Random

class VoidGenerator : ChunkGenerator() {

    override fun generateNoise(
        worldInfo: WorldInfo,
        random: Random,
        chunkX: Int,
        chunkZ: Int,
        chunkData: ChunkData
    ) {

    }
}