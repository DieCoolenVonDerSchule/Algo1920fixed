package de.thkoeln.inf.c_gruen.livecoding

class Tests{

    fun testHeapifyUp(){

        var heap = BinaryHeap()

        heap.addNode(1)
        heap.addNode(6)
        heap.addNode(4)
        heap.addNode(5)
        heap.addNode(2)
        heap.addNode(3)

        heap.heap[0].priority = 1
        heap.heap[1].priority = 2
        heap.heap[2].priority = 3
        heap.heap[3].priority = 6
        heap.heap[4].priority = 5
        heap.heap[5].priority = 5

    }

}