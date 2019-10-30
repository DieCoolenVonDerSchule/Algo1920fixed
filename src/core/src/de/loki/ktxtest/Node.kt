package de.loki.ktxtest

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import kotlin.math.pow

open class Node(var priority : Int, var posx : Float = 0f, var posy : Float = 0f){

    open val size : Float = 5f

    open fun draw(shapeRenderer: ShapeRenderer){
        shapeRenderer.circle(posx, posy, size)
    }

    open fun updatePosition(index : Int, listSize : Int){
        var row = MathUtils.floor(MathUtils.log(2f, (index + 1).toFloat()))
        var column = (index + 1) - 2.toDouble().pow(row.toDouble())

        var maxRow = MathUtils.floor(MathUtils.log(2f, listSize.toFloat())) + 1
        var maxColumn = 2.toDouble().pow(row)

        this.posx = ((Main.VIEWPORT_WIDTH / (maxColumn+1)) * (column+1)).toFloat()
        this.posy = Main.VIEWPORT_HEIGHT - (Main.VIEWPORT_HEIGHT / (maxRow+1) * (row+1))
    }

}