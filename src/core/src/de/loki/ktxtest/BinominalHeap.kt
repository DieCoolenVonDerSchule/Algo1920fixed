package de.loki.ktxtest

import com.badlogic.gdx.Gdx
import kotlin.random.Random

class BinominalHeap (){
    //Die Liste alle Trees im Heap
    var heap = mutableListOf<Tree>()

    init {
        for(i in 0 until 10){
            addNode(Random.nextInt(0, 10000))
        }
    }

    open fun poll() : Node{
        var smallest = Int.MAX_VALUE
        lateinit var tree : Tree

        for(t in heap){
            if(t.rootPriority < smallest){
                smallest = t.rootPriority
                tree = t
            }
        }
        return split(tree)
    }

    open fun split(tree : Tree) : Node{
        lateinit var node : Node
        if(tree is BinominalTreeDZero){
            node = tree.node
            heap.remove(tree)
            return node
        }
        else if(tree is BinominalTree){
            heap.add(tree.rightTree)
            node = split(tree.leftTree)
            heap.remove(tree)
        }
        return node
    }

    open fun correctTree(){
        var correction = true
        while(correction){
            correction = false
            for(t in heap){
                for(t2 in heap){
                    if(t.degree == t2.degree && t != t2){
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

    open fun addNode(priority : Int){
        heap.add(BinominalTreeDZero(Node(priority)))
        correctTree()
    }

//    open fun addNode(priority : Int){
//        var found = false
//        //Degree des Baums der grad verwaltet wird
//        var degree = 0
//        var tree : Tree = BinominalTreeDZero(Node(priority))
//
//        while(true){
//            for(n in heap){
//                if(n.degree == degree) found = true
//            }
//
//            if(!found){
//                heap.add(tree)
//                break
//            }
//            else{
//                for(t in heap){
//                    if(t.degree == degree){
//                        degree++
//                        if(t.rootPriority <= tree.rootPriority)tree = merge(degree, t, tree)
//                        else tree = merge(degree, tree, t)
//
//                        heap.remove(t)
//                        break
//                    }
//                }
//
//            }
//
//            found = false
//        }
//
//    }

    open fun isCorrect() : Boolean{
        var correct = true
        for(t in heap){
            for(t2 in heap){
                if(t.degree == t2.degree && !t.equals(t2)) correct = false
            }
        }
        return correct
    }

    open fun merge(degree : Int, tree1 : Tree, tree2 : Tree) : BinominalTree{
        return if (tree1.rootPriority <= tree2.rootPriority) BinominalTree(tree1.rootPriority,degree+1, tree1, tree2)
        else BinominalTree(tree2.rootPriority,degree+1, tree2, tree1)
    }

    open fun logAll(){
        for(t in heap){
            log(t)
        }

        var smallest = Int.MAX_VALUE

        for(t in heap){
            if(t.rootPriority < smallest) smallest = t.rootPriority
        }
        Gdx.app.log("Debug", "smallest: " + smallest)
    }

    open fun log(tree : Tree){
        if(tree is BinominalTree){
            log(tree.leftTree)
            log(tree.rightTree)
        } else if(tree is BinominalTreeDZero) {
            Gdx.app.log("debug", "Priority: " + tree.node.priority)
        }
    }

}