package de.loki.ktxtest

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import kotlin.math.pow
import kotlin.random.Random

class BinominalHeap (){
    //Die Liste alle Trees im Heap
    var heap = mutableListOf<Tree>()
    var lines = mutableListOf<Vector2>()
    var nodeCount = 0

    //Entnehmen der kleinsten Node-
    open fun poll() : Node{
        var smallest = Int.MAX_VALUE
        lateinit var tree : Tree

        //herrausfinden des kleinsten Wertes der Trees im Heap
        for(t in heap){
            if(t.rootNode.priority < smallest){
                smallest = t.rootNode.priority
                tree = t
            }
        }
        nodeCount--
        return split(tree)
    }

    //findet die Node mit dem kleinsten Wertes innerhalb des Trees (Root) und fügt die Teilbäume dem Heap hinzu
    open fun split(tree : Tree) : Node{
        lateinit var node : Node
        //falls der Wert der Root ist
        if(tree is BinominalTreeDZero){
            node = tree.node
            heap.remove(tree)
            return node
        }
        //removen des zu bearbeiteten Trees, adden des Teilbaums zum Heap und Rekursion für die kleinere Hälfte
        else if(tree is BinominalTree){
            heap.add(tree.rightTree)
            node = split(tree.leftTree)
            heap.remove(tree)
        }
        return node
    }

    //Sorgt dafür, das der Heap nur einen Baum des jeweiligen Grades hat
    open fun correctTree(){
        var correction = true
        while(correction){
            correction = false
            for(t in heap){
                for(t2 in heap){
                    //kontrolliert ob die Iterierten Bäume der selbe sind
                    if(t.degree == t2.degree && t != t2){
                        //Mergen der Bäume
                        heap.add(merge(t.degree, t, t2))
                        heap.remove(t)
                        heap.remove(t2)
                        correction = true
                        Gdx.app.log("Debug", "Correction!")
                        break
                    }
                }
            }
        }
    }

    //Hinzufügen einer Node bzw. eines Trees vom Grad 0
    open fun addNode(priority : Int){
        heap.add(BinominalTreeDZero(Node(priority)))
        //Korrigieren des Baums
        correctTree()
        nodeCount++
    }

    //Funktion zum Kontrollieren, ob der Heap vom jeden Baum Grad nur einen hat
    open fun isCorrect() : Boolean{
        var correct = true
        for(t in heap){
            for(t2 in heap){
                if(t.degree == t2.degree && !t.equals(t2)) correct = false
            }
        }
        return correct
    }

    //Mergen von zwei Trees bei dem der kleinere links eingefügt wird
    open fun merge(degree : Int, tree1 : Tree, tree2 : Tree) : BinominalTree{
        return if (tree1.rootNode.priority <= tree2.rootNode.priority) BinominalTree(tree1.rootNode,degree+1, tree1, tree2)
        else BinominalTree(tree2.rootNode,degree+1, tree2, tree1)
    }

    //Legt die Position aller Nodes auf dem Bildschirm fest
    open fun updateAllNodes(){
        var width = (2.toDouble().pow((Integer.toBinaryString(nodeCount).length - 1).toDouble())).toInt()

        for(i in 0 until heap.size){
            if(heap[i] is BinominalTree)updateTree(0, 0, heap[i] as BinominalTree, width - 2.toDouble().pow(heap[i].degree).toInt())
            else if(heap[i] is BinominalTreeDZero)updateTree(0, 0, heap[i] as BinominalTreeDZero, width - 2.toDouble().pow(heap[i].degree).toInt())
        }
    }

    //Funktion die Rekursiv ausgeführt wird, für den Fall, das der Übergebene Tree ein Binominal Tree ist
    //Zusätzlich hinzufügen der Linien zwischen zwei Teilbäumen des Grades 0
    open fun updateTree(depthX : Int, depthY : Int, tree : BinominalTree, offset : Int){
        if(tree.leftTree is BinominalTreeDZero && tree.rightTree is BinominalTreeDZero){
            var maxYDepth = Integer.toBinaryString(nodeCount).length+1
            var posx = (Main.VIEWPORT_WIDTH / (2.toDouble().pow((Integer.toBinaryString(nodeCount).length - 1).toDouble()) + 1) * (depthX+offset+1)).toFloat()

            (tree.rightTree as BinominalTreeDZero).node.posx = Main.VIEWPORT_WIDTH - posx
            (tree.rightTree as BinominalTreeDZero).node.posy = (Main.VIEWPORT_HEIGHT / maxYDepth * (depthY+1))

            (tree.leftTree as BinominalTreeDZero).node.posx = Main.VIEWPORT_WIDTH - posx
            (tree.leftTree as BinominalTreeDZero).node.posy = (Main.VIEWPORT_HEIGHT / maxYDepth * (depthY+2))

            lines.add(Vector2((tree.rightTree as BinominalTreeDZero).node.posx, (tree.rightTree as BinominalTreeDZero).node.posy))
            lines.add(Vector2((tree.leftTree as BinominalTreeDZero).node.posx, (tree.leftTree as BinominalTreeDZero).node.posy))
        } else if(tree.leftTree is BinominalTree && tree.rightTree is BinominalTree){
            updateTree(depthX*2, depthY, tree.rightTree as BinominalTree, offset)
            updateTree(depthX*2+1, depthY+1, tree.leftTree as BinominalTree, offset)
        }
    }

    //Funktion die Rekursiv ausgeführt wird, für den Fall, das der Übergebene Tree ein Binominal Tree 0ten Grades ist
    open fun updateTree(depthX : Int, depthY : Int, tree : BinominalTreeDZero, offset : Int){
        var maxYDepth = Integer.toBinaryString(nodeCount).length+1
        var posx = (Main.VIEWPORT_WIDTH / (2.toDouble().pow((Integer.toBinaryString(nodeCount).length - 1).toDouble()) + 1) * (depthX+offset+1)).toFloat()

        tree.node.posx = Main.VIEWPORT_WIDTH - posx
        tree.node.posy = (Main.VIEWPORT_HEIGHT / maxYDepth * (depthY+1))
    }

    //Zeichnet alle Linien auf dem Bilschirm
    open fun drawAllLines(shapeRenderer: ShapeRenderer){
        shapeRenderer.setColor(Color.WHITE)
        for(i in 0 until lines.size/2){
            shapeRenderer.rectLine(lines[i*2].x, lines[i*2].y, lines[i*2+1].x, lines[i*2+1].y, 10f)
        }
    }

    //Fügt alle notwendigen Linien der Liste aller Linien hinzu
    open fun addAllLines(){
        for(t in heap){
            if(t is BinominalTree)addLines(t)
        }
    }

    //Rekursive Funktion zum hinzufügen der Linien zwischen den Teilbäumen
    open fun addLines(tree : BinominalTree){
        if(tree.leftTree is BinominalTree && tree.rightTree is BinominalTree){
            lines.add(Vector2(tree.rootNode.posx, tree.rootNode.posy))
            lines.add(Vector2(tree.rightTree.rootNode.posx, tree.rightTree.rootNode.posy))

            addLines(tree.leftTree as BinominalTree)
            addLines(tree.rightTree as BinominalTree)
        }
    }

    //Für Kontrollzwecke, zeichnet die Werte aller Knoten
    open fun drawAllText(batch: SpriteBatch, font: BitmapFont, layout: GlyphLayout){
        for(t in heap){
            drawText(t, batch, font, layout)
        }
    }

    //Rekursives Ausgeben der Prioritäten des Baums
    open fun drawText(tree : Tree, batch: SpriteBatch, font: BitmapFont, layout: GlyphLayout){
        if(tree is BinominalTree){
            drawText(tree.leftTree, batch, font, layout)
            drawText(tree.rightTree, batch, font, layout)
        } else if(tree is BinominalTreeDZero) {
            tree.node.drawText(batch, font, layout)
        }
    }

    //Funktion zum Zeichnen aller Bäume
    open fun drawAllTrees(shapeRenderer: ShapeRenderer){
        shapeRenderer.setColor(Color.RED)
        for(t in heap){
            drawTree(t, shapeRenderer)
        }
    }

    //Rekursive Funktion zum Zeichnen eines Baumes
    open fun drawTree(tree : Tree, shapeRenderer: ShapeRenderer){
        if(tree is BinominalTree){
            drawTree(tree.rightTree, shapeRenderer)
            drawTree(tree.leftTree, shapeRenderer)
        } else if(tree is BinominalTreeDZero) {
            tree.node.draw(shapeRenderer)
        }
    }

    //Für Kontrolle Zwecke, ausgeben der Prioritäten des Heaps
    open fun logAll(){
        for(t in heap){
            log(t)
        }
    }

    //Rekursives Ausgeben der Prioritäten des Baums
    open fun log(tree : Tree){
        if(tree is BinominalTree){
            log(tree.leftTree)
            log(tree.rightTree)
        } else if(tree is BinominalTreeDZero) {
            Gdx.app.log("debug", "posx: " + tree.node.posx + " posy: " + tree.node.posy)
        }
    }

    //Leeren der List aller Linien
    open fun clearLines(){
        lines.clear()
    }

}