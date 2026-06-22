import dev.slne.surf.api.gradle.util.registerSoft

plugins {
    id("dev.slne.surf.api.gradle.paper-plugin")
}

dependencies {
    compileOnly("dev.slne.surf.npc:surf-npc-api:+")
    implementation(files("api/nex-event-base-api-1.0.jar"))
    implementation(files("api/nex-parkour-api-3.1.0.jar"))
}

version = findProperty("version") as String
group = "dev.hexoria.hxo"

surfPaperPluginApi {
    mainClass("dev.hexoria.hxo.lobby.PaperMain")
    generateLibraryLoader(false)
    withSurfRedis()

    authors.add("HiorCraft")


    serverDependencies {
        registerSoft("surf-npc-paper")
    }

}
