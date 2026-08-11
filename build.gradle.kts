import dev.slne.surf.api.gradle.util.registerRequired
import dev.slne.surf.api.gradle.util.registerSoft

plugins {
    id("dev.slne.surf.api.gradle.paper-plugin")
}

repositories {
    maven("https://reposilite.hxo-development.dev/releases")
}

dependencies {
    compileOnly("dev.slne.surf.npc:surf-npc-api:+")
    compileOnly("net.luckperms:api:5.4")
    compileOnly("io.github.miniplaceholders:miniplaceholders-api:3.0.1")
    compileOnly("io.github.miniplaceholders:miniplaceholders-kotlin-ext:3.0.1")

    implementation("dev.hexoria.hxo.profile:hxo-profile-api:+")
}

version = findProperty("version") as String
group = "dev.hexoria.hxo"

surfPaperPluginApi {
    mainClass("dev.hexoria.hxo.lobby.PaperMain")
    generateLibraryLoader(false)

    withSurfRedis()
    withCorePaper()

    authors.add("HiorCraft")

    serverDependencies {
        registerSoft("surf-npc-paper")
        registerSoft("hxo-profile-paper")
    }
}