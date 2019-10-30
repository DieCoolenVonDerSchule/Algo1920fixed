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

    //Entnehmen der kleinsten Node-
    open fun poll() : Node{
        var smallest = Int.MAX_VALUE
        lateinit var tree : Tree

        //herrausfinden des kleinsten Wertes der Trees im Heap
        for(t in heap){
            if(t.rootPriority < smallest){
                smallest = t.rootPriority
                tree = t
            }
        }
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
        return if (tree1.rootPriority <= tree2.rootPriority) BinominalTree(tree1.rootPriority,degree+1, tree1, tree2)
        else BinominalTree(tree2.rootPriority,degree+1, tree2, tree1)
    }

    //Für Kontrolle Zwecke, ausgeben der Prioritäten des Heaps
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

    //Rekursives Ausgeben der Prioritäten des Baums
    open fun log(tree : Tree){
        if(tree is BinominalTree){
            log(tree.leftTree)
            log(tree.rightTree)
        } else if(tree is BinominalTreeDZero) {
            Gdx.app.log("debug", "Priority: " + tree.node.priority)
        }
    }

}