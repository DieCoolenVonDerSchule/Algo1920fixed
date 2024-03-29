package de.thkoeln.inf.c_gruen.livecoding.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import de.thkoeln.inf.c_gruen.livecoding.Main

//Main Funktion zum Starten des Programms
object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()

        config.width = 2560
        config.height = 1440
        config.fullscreen = false
        config.resizable = false

        LwjglApplication(Main(), config)
    }
}
