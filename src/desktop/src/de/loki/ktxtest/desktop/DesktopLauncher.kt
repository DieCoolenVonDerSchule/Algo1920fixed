package de.loki.ktxtest.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import de.loki.ktxtest.Main

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()

        config.width = 3840
        config.height = 2160
        config.fullscreen = false
        config.resizable = false

        LwjglApplication(Main(), config)
    }
}
