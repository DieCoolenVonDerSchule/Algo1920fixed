package de.loki.ktxtest

//Oberklasse mit der Priorität der Root Node und dem Degree des Baums
open class Tree(var rootNode: Node, val degree : Int)

//Spezielle Klasse für den Baum mit Grad 0, weil dieser als einziger eine Node enthalten muss
class BinominalTreeDZero(var node : Node, degree: Int = 0) : Tree(node, degree)

//Normaler (Teil)Baum für den Heap
open class BinominalTree(rootNode: Node, degree : Int, var leftTree : Tree, var rightTree : Tree) : Tree(rootNode, degree){

}