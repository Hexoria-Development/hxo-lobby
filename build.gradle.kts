plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin") version "1.21.11+"
}

repositories {
    maven("https://repo.nexomc.com/releases")
}

version = findProperty("version") as String
group = "de.hiorcraft.nex.nexLobby"

surfPaperPluginApi {
    mainClass("de.hiorcraft.nex.nexLobby.PaperMain")
    generateLibraryLoader(false)

    authors.add("Hiorcraft")
}
