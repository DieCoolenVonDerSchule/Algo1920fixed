package de.thkoeln.inf.c_gruen.main

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import kotlin.math.pow

open class Node(var priority : Int, var posx : Float = 0f, var posy : Float = 0f){

    //Größes des Kreises
    open val size : Float = 50f

    //Zeichnen des Kreises
    open fun draw(shapeRenderer: ShapeRenderer){
        shapeRenderer.circle(posx, posy, size)
    }

    open fun drawText(batch : SpriteBatch, font : BitmapFont, layout : GlyphLayout){
        layout.setText(font, "" + priority)
        font.setColor(Color.WHITE)
        font.draw(batch, "" + priority, posx-layout.width/2, posy+layout.height/2)
    }

    //Updaten der Position der Node
    open fun updatePosition(index : Int, listSize : Int){
        var row = MathUtils.floor(MathUtils.log(2f, (index + 1).toFloat()))
        var column = (index + 1) - 2.toDouble().pow(row.toDouble())

        var maxRow = MathUtils.floor(MathUtils.log(2f, listSize.toFloat())) + 1
        var maxColumn = 2.toDouble().pow(row)

        this.posx = ((Main.VIEWPORT_WIDTH / (maxColumn+1)) * (column+1)).toFloat()
        this.posy = Main.VIEWPORT_HEIGHT - (Main.VIEWPORT_HEIGHT / (maxRow+1) * (row+1))
    }

}