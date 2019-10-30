package de.loki.ktxtest

open class Tree(var rootPriority: Int, val degree : Int)

class BinominalTreeDZero(var node : Node, degree: Int = 0) : Tree(node.priority, degree)

open class BinominalTree(rootPriority: Int, degree : Int, var leftTree : Tree, var rightTree : Tree) : Tree(rootPriority, degree){

}