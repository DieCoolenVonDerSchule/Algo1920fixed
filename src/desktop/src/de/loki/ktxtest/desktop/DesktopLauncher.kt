package de.loki.ktxtest.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import de.loki.ktxtest.Main

//Main Funktion zum Starten des Programms
object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()

        config.width = 1920
        config.height = 1080
        config.fullscreen = false
        config.resizable = false

        LwjglApplication(Main(), config)
    }
}
