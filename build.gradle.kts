import dev.slne.surf.api.gradle.util.registerSoft

plugins {
    id("dev.slne.surf.api.gradle.paper-plugin")
}

dependencies {
    compileOnly("dev.slne.surf.npc:surf-npc-api:+")
}

version = findProperty("version") as String
group = "de.hiorcraft.nex"

surfPaperPluginApi {
    mainClass("de.hiorcraft.nex.Lobby.PaperMain")
    generateLibraryLoader(false)

    authors.add("HiorCraft")


    serverDependencies {
        registerSoft("surf-npc-paper")
    }

}
