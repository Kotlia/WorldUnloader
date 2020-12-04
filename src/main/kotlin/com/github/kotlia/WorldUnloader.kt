package com.github.kotlia

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.plugin.java.JavaPlugin

class WorldUnloader : JavaPlugin() {
    override fun onLoad() {
        logger.info("WorldUnloader Ready...")
        saveDefaultConfig()
    }
    override fun onEnable() {
        plugin = this
        server.pluginManager.registerEvents(EventListener(), this)
        worlds = config.getStringList("world")
        worlds.forEach {
            server.dispatchCommand(
                server.consoleSender,
                "mv unload $it"
            )
        }
    }
    class EventListener : Listener {
        @EventHandler
        fun onCommand(e: PlayerCommandPreprocessEvent) {
            val command = e.message.substring(1).split(" ")
            if(command[0] == "mvtp") {
                plugin.server.dispatchCommand(
                    plugin.server.consoleSender,
                    "mv load ${command[1]}"
                )
            }
        }
    }
    companion object {
        lateinit var worlds: MutableList<String>
        lateinit var plugin: JavaPlugin
    }
}