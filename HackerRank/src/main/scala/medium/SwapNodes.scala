package medium

import scala.collection.mutable
import scala.io.StdIn.{readInt, readLine}

// https://www.hackerrank.com/challenges/swap-nodes/problem
object SwapNodes {

  case class Tree(val tree: Array[Node]) {
    def clone(tree: Array[Node]): Tree = new Tree(tree.clone())

    def inOrderTraveral(): Unit = {
      val result = mutable.ArrayBuffer[Int]()
      def helper(currentIndex: Int,
                 currentTraversalIndex: Int): Unit = {
        if(currentIndex != -2){
          val currentNode = tree(currentIndex)

          helper(currentNode.left,0)
          result.append(currentIndex+1)
          helper(currentNode.right,0)
        }

      }
      helper(0,0)
      println(result.mkString(" "))
    }

    def swapNodes(SwapDepth: Int): Unit = {
      def helper(currentDepth: Int, currentNodeIndex: Int): Unit = {
        val currentNode = tree(currentNodeIndex)
        if(currentDepth % SwapDepth == 0){
          tree(currentNodeIndex) =
            Node(currentNode.right,currentNode.left)
        }

        if(currentNode.left != -2) helper(currentDepth+1, currentNode.left)
        if(currentNode.right != -2) helper(currentDepth+1, currentNode.right)
      }

      helper(1,0)
    }
  }

  case class Node(left:Int, right: Int)
  case object Empty

  def main(args: Array[String]): Unit = {
    val N = readInt()
    val binaryTree = new Tree((for (_ <- 1 to N) yield {
      readLine().split(" ").map(el => el.toInt) match {
        case Array(left,right) => Node(left-1,right-1)
      }
    } ).toArray)

    val T = readInt()
    val T_L_I = (for (_ <- 1 to T) yield readInt()).toList

    T_L_I.foreach(swapIndex => {
      binaryTree.swapNodes(swapIndex)
      binaryTree.inOrderTraveral()
    })

  }

}
