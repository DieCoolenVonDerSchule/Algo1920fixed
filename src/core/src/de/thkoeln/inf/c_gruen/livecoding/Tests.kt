package de.thkoeln.inf.c_gruen.livecoding

class Tests{

    companion object{
        fun testHeapifyUp(){

            var heap = BinaryHeap()

            heap.addNode(1)
            heap.addNode(6)
            heap.addNode(4)
            heap.addNode(5)
            heap.addNode(2)
            heap.addNode(3)

            var check = 0

            for(n in heap.heap){
                println(n.priority)
            }

            if(heap.heap[0].priority == 1) check++
            if(heap.heap[1].priority == 2) check++
            if(heap.heap[2].priority == 3) check++
            if(heap.heap[3].priority == 6) check++
            if(heap.heap[4].priority == 5) check++
            if(heap.heap[5].priority == 4) check++

            if(check >= 6) println("Test Erfolgreich!")

        }
    }

}