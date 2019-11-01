package de.loki.ktxtest

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import kotlin.random.Random

class BinaryHeap(){

    var heap = mutableListOf<Node>()

    fun getLeftChildIndex(index : Int) = 2 * index + 1                      // liefert den linken Child-Index der Node
    fun getRightChildIndex(index : Int) = 2 * index + 2                     // liefert den rechten Child-Index der Node
    fun getParentIndex(childIndex: Int) = (childIndex - 1) / 2              // liefert den Parent-Index der Node

    fun hasLeftChild(index : Int) = getLeftChildIndex(index) < heap.size    // liefert True wenn linkes Child vorhanden
    fun hasRightChild(index : Int) = getRightChildIndex(index) < heap.size  // liefert True wenn rechtes Child vorhanden
    fun hasParent(index: Int) : Boolean = getParentIndex(index) >= 0        // liefert True wenn Parent vorhanden

    fun getLeftChildX(index : Int) = heap[getLeftChildIndex(index)].posx    // liefert die X Position des linkes Childs
    fun getLeftChildY(index : Int) = heap[getLeftChildIndex(index)].posy    // liefert die Y Position des linkes Childs

    fun getRightChildX(index : Int) = heap[getRightChildIndex(index)].posx  // liefert die X Position des rechten Childs
    fun getRightChildY(index : Int) = heap[getRightChildIndex(index)].posy  // liefert die Y Position des rechten Childs

    fun leftChild(index: Int) : Int = heap[getLeftChildIndex(index)].priority // liefert die Priority des linken Childs
    fun rightChild(index: Int) : Int = heap[getRightChildIndex(index)].priority//liefert die Priority des rechten Childs
    fun parent(index: Int) : Int = heap[getParentIndex(index)].priority     // liefert die Priority der Parent Node



    fun drawAll(shapeRenderer: ShapeRenderer){
        shapeRenderer.setColor(Color.RED)
        for(n in heap){
            n.draw(shapeRenderer)
        }
    }

    fun drawAllLines(shapeRenderer: ShapeRenderer){
        shapeRenderer.setColor(Color.WHITE)

        for(i in 0 until heap.size){
            if(hasLeftChild(i)) shapeRenderer.rectLine(heap[i].posx, heap[i].posy, getLeftChildX(i), getLeftChildY(i), 10f)
            if(hasRightChild(i)) shapeRenderer.rectLine(heap[i].posx, heap[i].posy, getRightChildX(i), getRightChildY(i), 10f)
        }
    }

    fun addNode(priority : Int){                       // neuer Knoten wird dem Heap hinzugefügt
        heap.add(Node(priority))
        heapifyUp()
    }

    fun updateAllNodes(){                             // legt die Position aller Knoten auf dem Bildschirm fest
        for(i in 0 until heap.size){
            heap[i].updatePosition(i, heap.size)
        }
    }


    fun swap(indexOne: Int, indexTwo: Int) {          // Knoten mit IndexOne und IndexTwo werden vertauscht
        val temp = heap[indexOne]
        heap[indexOne] = heap[indexTwo]
        heap[indexTwo] = temp
    }


    fun peek() : Node {                               // liefert die Wurzel des Baums
        if (heap.size == 0) throw IllegalStateException()
        return heap[0]
    }


    fun poll() : Node {                              // liefert und entfernt die Wurzel des Baums
        if (heap.size == 0) throw IllegalStateException()
        val item : Node = heap[0]
        heap[0] = heap[heap.size - 1]
        heapifyDown()
        return item

    }


    fun heapifyUp() {                                // Sortieren des letzten Knotens nach oben
        var index = heap.size - 1
        while (hasParent(index) && parent(index) > heap[index].priority) {
            swap(getParentIndex(index),index)
            index = getParentIndex(index)
        }

    }


    fun heapifyDown() {                             // Sortieren des ersten Knotens (Wurzel) nach unten
        var index = 0
        while (hasLeftChild(index)) {
            var smallerChildIndex : Int = getLeftChildIndex(index)
            if (hasRightChild(index) && rightChild(index) < leftChild(index)) {
                smallerChildIndex = getRightChildIndex(index)
            }

            if (heap[index].priority < heap[smallerChildIndex].priority) {
                break
            } else {
                swap(index, smallerChildIndex)
            }
            index = smallerChildIndex

        }


    }



}