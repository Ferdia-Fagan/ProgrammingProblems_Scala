package medium

import scala.annotation.tailrec
import scala.collection.mutable
import scala.io.StdIn.{readInt, readLine}


// https://www.hackerrank.com/challenges/valid-bst/problem
object ValidBST {

  trait Node
  case class EmptyTree() extends Node
  case class TreeNode(var value: Int, var left: TreeNode = null, var right: TreeNode = null) extends Node

//  def constructTreeFromList[T](n: List[Int]): Node = {
//
////    var rootStack = new mutable.Stack[TreeNode]()
////    var stackOfN = mutable.Stack[Int](n.tail:_*)
//
////    def addElToBST(el: Int, tree: TreeNode): TreeNode = {
////      if(el > tree.value) {
////        if(tree.right != null) {
////          addElToBST(el, tree)
////        }else {
////          tree.right = TreeNode(el)
////          tree
////        }
////      }else {
////        if(tree.left != null) {
////
////        }
////      }
////    }
//
//    var tree = TreeNode(n.head,EmptyTree(),EmptyTree())
//
//    @tailrec
//    def addElToBST(el: Int): Unit = {
//      if(tree.value != -1) {
//        if(el > tree.value) {
//          addElToBST(el, tree.right)
//        }else {
//          addElToBST(el, tree.left)
//        }
//      }else {
//        tree.value = el
//        return
//      }
//    }
////    rootStack.push(tree)
//    n.tail.foreach(el => {
//      tree = addElToBST()
//    })
//
//    while(rootStack.nonEmpty) {
//      val currentRoot = rootStack.pop()
//
//
//    }
//
////    if(n.nonEmpty){
////      var tree = new Array[TreeNode](n.length)
////      var stack = new mutable.Stack[]()
////    }else{
////      EmptyTree()
////    }
////
////    var tree: Node = EmptyTree()
////
////
////
////    def helper(current): Boolean = {
//
//    }
////    n match {
////    case ::(head, next) => {
////      if
////    }
////    case Nil =>
////  }
//  }
//
//  def validBST(N: List[Int]): Unit = {
//
//    def helper(n: List[Int]): List[Int]
//
//
//  }

  def preOrderConstTree(n: List[Int]): Boolean = {

    var stack = new mutable.Stack[Int]()

    var currentRoot = Int.MinValue

    n.foreach(el => {
      if(el < currentRoot) {
        return false
      }else{

        while(stack.nonEmpty && stack.head < el){
          currentRoot = stack.pop()
        }

        stack.push(el)
      }
    })

    return true

  }

  def main(args: Array[String]): Unit = {
    val T = readInt()

    for(_ <- 1 to T){
      readInt()
      val N = readLine().split(" ").map(_.toInt).toList
      println(if(preOrderConstTree(N)) "YES" else "NO")
//      validBST(N)
    }
  }

}
