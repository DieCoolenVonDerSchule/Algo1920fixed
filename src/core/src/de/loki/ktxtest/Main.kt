package de.loki.ktxtest

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport

class Main : ApplicationAdapter() {
    companion object{
        var aspect_ratio = 0f
        val VIEWPORT_WIDTH = 3840f
        var VIEWPORT_HEIGHT = 0f
        var scale = 0f
    }

    lateinit var shapeRenderer: ShapeRenderer
    lateinit var camera : OrthographicCamera
    lateinit var viewport : Viewport
    lateinit var heap : BinaryHeap
    var dotTimer : Float = 0f

    override fun create() {
        aspect_ratio = Gdx.graphics.height / Gdx.graphics.width.toFloat()
        scale = Gdx.graphics.getWidth() / VIEWPORT_WIDTH
        VIEWPORT_HEIGHT = VIEWPORT_WIDTH*aspect_ratio

        shapeRenderer = ShapeRenderer()
        camera = OrthographicCamera()
        viewport = FitViewport(VIEWPORT_WIDTH, VIEWPORT_WIDTH*aspect_ratio, camera)
        viewport.apply()

        camera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_WIDTH * aspect_ratio / 2f, 0f)
        heap = BinaryHeap()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        //dotTimer += Gdx.graphics.deltaTime
        if(dotTimer >= 0.2f){
            heap.addNode(8)
            heap.updateAllNodes()
            dotTimer = 0f
        }

        shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        heap.drawAllLines(shapeRenderer)
        heap.drawAll(shapeRenderer)

        shapeRenderer.end()

    }

    override fun dispose() {
        shapeRenderer.dispose()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        viewport.update(width, height)
        camera.position.set(VIEWPORT_WIDTH/2, VIEWPORT_HEIGHT, 0f)
    }
}
