package dev.hexoria.hxo.lobby.utils

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World

enum class Locations(
    val world: World,
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float,
    val pitch: Float
) {
    SMASH_NPC(
        world = Bukkit.getWorlds().first(),
        x = -24.5,
        y = 73.0,
        z = 24.5,
        yaw = 90.0f,
        pitch = 0.0f
    ),

    UHC_NPC(
        world = Bukkit.getWorlds().first(),
        x = -22.5,
        y = 73.0,
        z = 22.5,
        yaw = 90.0f,
        pitch = 0.0f
    ),

    RIB_NPC(
        world = Bukkit.getWorlds().first(),
        x = -24.5,
        y = 73.0,
        z = 30.5,
        yaw = 90.0f,
        pitch = 0.0f
    ),

    EVENT_NPC(
        world = Bukkit.getWorlds().first(),
        x = -25.5,
        y = 73.0,
        z = 27.5,
        yaw = 0.0f,
        pitch = 0.0f
    ),

    RUBINE_NPC(
        world = Bukkit.getWorlds().first(),
        x = 20.5,
        y = 70.0,
        z = 6.5,
        yaw = -37.0f,
        pitch = 0.0f
    ),

    UNKNOWN_NPC(
        world = Bukkit.getWorlds().first(),
        x = -22.5,
        y = 73.0,
        z = 32.5,
        yaw = -37.0f,
        pitch = 0.0f
    ),

    SHOP_NPC(
        world = Bukkit.getWorlds().first(),
        x = 22.5,
        y = 68.0,
        z = 41.5,
        yaw = -37.0f,
        pitch = 0.0f
    );

    fun getLocation() =
        Location(world, x, y, z, yaw, pitch)
}