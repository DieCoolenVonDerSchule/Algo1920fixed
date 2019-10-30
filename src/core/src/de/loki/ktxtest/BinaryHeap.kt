package de.loki.ktxtest

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class BinaryHeap(){

    var heap = mutableListOf<Node>()

    fun getLeftChildIndex(index : Int) = 2 * index + 1
    fun getRightChildIndex(index : Int) = 2 * index + 2

    fun hasLeftChild(index : Int) = getLeftChildIndex(index) < heap.size
    fun hasRightChild(index : Int) = getRightChildIndex(index) < heap.size

    fun getLeftChildX(index : Int) = heap[getLeftChildIndex(index)].posx
    fun getLeftChildY(index : Int) = heap[getLeftChildIndex(index)].posy

    fun getRightChildX(index : Int) = heap[getRightChildIndex(index)].posx
    fun getRightChildY(index : Int) = heap[getRightChildIndex(index)].posy

    init {
        for(i in 0 until 1000){
            addNode(5)
        }
        updateAllNodes()
    }

    fun drawAll(shapeRenderer: ShapeRenderer){
        shapeRenderer.setColor(Color.RED)
        for(n in heap){
            n.draw(shapeRenderer)
        }
    }

    fun drawAllLines(shapeRenderer: ShapeRenderer){
        shapeRenderer.setColor(Color.WHITE)

        for(i in 0 until heap.size){
            if(hasLeftChild(i)) shapeRenderer.rectLine(heap[i].posx, heap[i].posy, getLeftChildX(i), getLeftChildY(i), 1f)
            if(hasRightChild(i)) shapeRenderer.rectLine(heap[i].posx, heap[i].posy, getRightChildX(i), getRightChildY(i), 1f)
        }
    }

    fun addNode(priority : Int){
        heap.add(Node(priority))
    }

    fun updateAllNodes(){
        for(i in 0 until heap.size){
            heap[i].updatePosition(i, heap.size)
        }
    }

}