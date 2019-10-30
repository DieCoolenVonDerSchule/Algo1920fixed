package de.loki.ktxtest

import com.badlogic.gdx.Gdx
import kotlin.random.Random

class BinominalHeap (){
    var heap = mutableListOf<Tree>()

    init {
        for(i in 0..10){
            addNode(Random.nextInt(0, 10000))
        }
    }

    open fun addNode(priority : Int){
        var found = false
        var degree = 0
        var tree : Tree = BinominalTreeDZero(Node(priority))

        while(true){
            for(n in heap){
                if(n.degree == degree) found = true
            }

            if(!found){
                heap.add(tree)
                break
            }
            else{
                for(t in heap){
                    if(t.degree == degree){
                        degree++
                        if(t.rootPriority <= tree.rootPriority)tree = merge(degree, t, tree)
                        else tree = merge(degree, tree, t)

                        heap.remove(t)
                        break
                    }
                }

            }

            found = false
        }

    }

    open fun merge(degree : Int, tree1 : Tree, tree2 : Tree) : BinominalTree = BinominalTree(tree1.rootPriority,degree+1, tree1, tree2)

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