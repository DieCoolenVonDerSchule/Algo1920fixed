package de.loki.ktxtest

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import kotlin.random.Random

class BinaryHeap(){

    var heap = mutableListOf<Node>()

    fun getLeftChildIndex(index : Int) = 2 * index + 1
    fun getRightChildIndex(index : Int) = 2 * index + 2
    fun getParentIndex(childIndex: Int) = (childIndex - 1) / 2

    fun hasLeftChild(index : Int) = getLeftChildIndex(index) < heap.size
    fun hasRightChild(index : Int) = getRightChildIndex(index) < heap.size
    fun hasParent(index: Int) : Boolean = getParentIndex(index) >= 0

    fun getLeftChildX(index : Int) = heap[getLeftChildIndex(index)].posx
    fun getLeftChildY(index : Int) = heap[getLeftChildIndex(index)].posy

    fun getRightChildX(index : Int) = heap[getRightChildIndex(index)].posx
    fun getRightChildY(index : Int) = heap[getRightChildIndex(index)].posy

    fun leftChild(index: Int) : Int = heap[getLeftChildIndex(index)].priority
    fun rightChild(index: Int) : Int = heap[getRightChildIndex(index)].priority
    fun parent(index: Int) : Int = heap[getParentIndex(index)].priority


    init {

        for(i in 0 until 100){
            val r : Int = Random.nextInt(1,100)
            addNode(r)
        }

        updateAllNodes()

    /*    addNode(5)
        addNode(18)
        addNode(7)
        addNode(12)
        addNode(8)
        addNode(75)
    */

     //   updateAllNodes()



        /*
        // POLL TEST

        println(heap[0].priority)
        println(heap[1].priority)
        println(heap[2].priority)
        println(heap[3].priority)
        println(heap[4].priority)
        println(heap[5].priority)

        println("")
       println(poll().priority)
        println("")

        println(heap[0].priority)
        println(heap[1].priority)
        println(heap[2].priority)
        println(heap[3].priority)
        println(heap[4].priority)
        println(heap[5].priority)

        updateAllNodes()
*/


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
            if(hasLeftChild(i)) shapeRenderer.rectLine(heap[i].posx, heap[i].posy, getLeftChildX(i), getLeftChildY(i), 10f)
            if(hasRightChild(i)) shapeRenderer.rectLine(heap[i].posx, heap[i].posy, getRightChildX(i), getRightChildY(i), 10f)
        }
    }

    fun addNode(priority : Int){
        heap.add(Node(priority))
        heapifyUp()
    }

    fun updateAllNodes(){
        for(i in 0 until heap.size){
            heap[i].updatePosition(i, heap.size)
        }
    }


    fun swap(indexOne: Int, indexTwo: Int) {
        val temp = heap[indexOne]
        heap[indexOne] = heap[indexTwo]
        heap[indexTwo] = temp
    }


    fun peek() : Int {
        if (heap.size == 0) throw IllegalStateException()
        return heap[0].priority
    }


    fun poll() : Node {
        if (heap.size == 0) throw IllegalStateException()
        val item : Node = heap[0]
        heap[0] = heap[heap.size - 1]
        heapifyDown()
        return item

    }

    fun add(item: Int) {
        heap[heap.size] = Node(item)
        heapifyUp()

    }


    fun heapifyUp() {
        var index = heap.size - 1
        while (hasParent(index) && parent(index) > heap[index].priority) {
            swap(getParentIndex(index),index)
            index = getParentIndex(index)
        }

    }



    fun heapifyDown() {
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