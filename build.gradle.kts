import dev.slne.surf.surfapi.gradle.util.registerSoft

plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin") version "1.21.11+"
}

repositories {
    maven("https://repo.nexomc.com/releases")
}

dependencies {
    compileOnly("dev.slne.surf.npc:surf-npc-api:1.21.11-1.6.0-SNAPSHOT")
}

version = findProperty("version") as String
group = "de.hiorcraft.nex"

surfPaperPluginApi {
    mainClass("de.hiorcraft.nex.Lobby.PaperMain")
    generateLibraryLoader(false)

    authors.add("Hiorcraft")


    serverDependencies {
        registerSoft("surf-npc-paper")
    }
}
