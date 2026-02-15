import dev.slne.surf.surfapi.gradle.util.registerSoft
import dev.slne.surf.surfapi.gradle.util.withSurfApiBukkit

plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

dependencies {
    compileOnly("dev.slne.surf.npc:surf-npc-api:1.21.10-1.5.0-20251009.154819-1")
}

group = "de.hiorcraft.nex.nexchat"
version = findProperty("version") as String

surfPaperPluginApi {
    mainClass("de.hiorcraft.nex.nexlobby.Papermain")
    authors.add("HiorCraft")

}