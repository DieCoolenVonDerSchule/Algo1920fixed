package de.thkoeln.inf.c_gruen.livecoding

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import kotlin.random.Random


class Main : ApplicationAdapter() {
    //Statische Werte für die Grafische Oberfläsche
    companion object{
        var aspect_ratio = 0f
        const val VIEWPORT_WIDTH = 3840f
        var VIEWPORT_HEIGHT = 0f
        var scale = 0f
        var binary = true //true = Binary Heap wird gezeichnet - false = Binominal Heap wird gezeichnet
    }

    //Benötigte Objekte für die Grafische Oberfläsche
    lateinit var shapeRenderer: ShapeRenderer
    lateinit var camera : OrthographicCamera
    lateinit var viewport : Viewport
    lateinit var font : BitmapFont
    lateinit var layout : GlyphLayout
    lateinit var batch : SpriteBatch
    var dotTimer : Float = 0f

    //Erstellen des BinaryHeaps und des BinominalHeaps
    lateinit var heap : BinaryHeap
    lateinit var biHeap : BinominalHeap

    override fun create() {
        //Errechnen der statischen Werte
        aspect_ratio = Gdx.graphics.height / Gdx.graphics.width.toFloat()
        scale = Gdx.graphics.getWidth() / VIEWPORT_WIDTH
        VIEWPORT_HEIGHT = VIEWPORT_WIDTH * aspect_ratio

        font = BitmapFont()
        font.data.scale(4f)
        font.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        layout = GlyphLayout()
        batch = SpriteBatch()

        //Erzeugen der Objete für die grafische Oberfläsche
        shapeRenderer = ShapeRenderer()
        camera = OrthographicCamera()
        viewport = FitViewport(VIEWPORT_WIDTH, VIEWPORT_WIDTH * aspect_ratio, camera)
        viewport.apply()

        camera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_WIDTH * aspect_ratio / 2f, 0f)

        //Erzeugen der Heaps und Log Funktion als Test
        heap = BinaryHeap()
        biHeap = BinominalHeap()

        biHeap.logAll()

        Tests.testHeapifyUp()
    }

    //Wird 60 mal die Sekunde ausgeführt
    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        //Hinzufügen eines Knotens alle 0.2 Sekunden falls nicht auskommentiert
        dotTimer += Gdx.graphics.deltaTime
        if(dotTimer >= 0.2f){

            if(binary){
                heap.addNode(Random.nextInt(0, 100))
                heap.updateAllNodes()
            }else{
                biHeap.addNode(Random.nextInt(0, 100))
                biHeap.clearLines()
                biHeap.updateAllNodes()
                biHeap.addAllLines()
            }

            dotTimer = 0f
        }

        //Zeichnen der Nodes
        shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        if(binary){
            heap.drawAllLines(shapeRenderer)
            heap.drawAll(shapeRenderer)
        }
        else{
            biHeap.drawAllLines(shapeRenderer)
            biHeap.drawAllTrees(shapeRenderer)
        }

        shapeRenderer.end()

        batch.projectionMatrix = camera.combined
        batch.begin()
        if(binary)heap.drawAllText(batch, font, layout)
        else biHeap.drawAllText(batch, font, layout)
        batch.end()

    }
    //Notwendige Funktionen für die Library
    override fun dispose() {
        shapeRenderer.dispose()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        viewport.update(width, height)
        camera.position.set(VIEWPORT_WIDTH /2, VIEWPORT_HEIGHT, 0f)
    }
}
