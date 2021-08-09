package medium

import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn.{readInt, readLine}

// https://www.hackerrank.com/challenges/tree-manager/problem
object TreeManager {

  case class TreeNode[A](var value: A = 0,
                         var parent: TreeNode[A] = null,
                         var children: ArrayBuffer[TreeNode[A]] = ArrayBuffer[TreeNode[A]]()
                        )

  class TreeManagerObj[A](var currentNode: TreeNode[A]){

    def changeValue(x: A): Unit = {
      currentNode.value = x
    }

    def print(): Unit = {
      println(currentNode.value)
    }

    def visitLeft(): Unit = {
      val index = currentNode.parent.children.indexOf(currentNode)
      currentNode = currentNode.parent.children(index-1)
//      currentNode = currentNode.children.head
    }

    def visitRight(): Unit = {
      val index = currentNode.parent.children.indexOf(currentNode)
      currentNode = currentNode.parent.children(index+1)
//      currentNode = currentNode.children.last
    }

    def visitParent(): Unit = {
      currentNode = currentNode.parent
    }

    def visitChild(n: Int): Unit = {
      currentNode = currentNode.children(n-1)
    }

    def insertLeft(x: A): Unit = {
      val index = currentNode.parent.children.indexOf(currentNode)
      currentNode.parent.children.insert(
        index,
        new TreeNode[A](x,currentNode.parent)
      )
    }

    def insertRight(x: A): Unit = {
      val index = currentNode.parent.children.indexOf(currentNode)
      currentNode.parent.children.insert(
        index + 1,
        new TreeNode[A](x,currentNode.parent)
      )
    }

    def insertChild(x: A): Unit = {
      currentNode.children.prepend(
        new TreeNode[A](x,currentNode)
      )
    }

    def delete(): Unit = {
      val index = currentNode.parent.children.indexOf(currentNode)

      currentNode.parent.children
        .remove(index)

      currentNode = currentNode.parent

    }
  }


  def main(args: Array[String]): Unit = {
    val Q = readInt()
    val treeNode = TreeNode()
    val treeManagerObj = new TreeManagerObj(treeNode)

    def performAction(command: String): Unit = {
      if(command.startsWith("change")){
        val x = command.split(" ").last.toInt
        treeManagerObj.changeValue(x)
      }

      else if(command.startsWith("print")){
        treeManagerObj.print()
      }

      else if(command.startsWith("visit")){
        if(command.endsWith("left")){
          treeManagerObj.visitLeft()
        }
        else if(command.endsWith("right")){
          treeManagerObj.visitRight()
        }
        else if(command.endsWith("parent")){
          treeManagerObj.visitParent()
        }
        else{
          val x = command.split(" ").last.toInt
          treeManagerObj.visitChild(x)
        }
      }

      else if(command.startsWith("insert")) {
        val commandParams = command.split(" ").toList
        if(commandParams(1) == "left") {
          treeManagerObj.insertLeft(commandParams.last.toInt)
        }
        else if(commandParams(1) == "right") {
          treeManagerObj.insertRight(commandParams.last.toInt)
        }
        else {
          treeManagerObj.insertChild(commandParams.last.toInt)
        }
      }

      else if(command.startsWith("delete")){
        treeManagerObj.delete()
      }
    }

    for(_ <- 1 to Q){
      val command = readLine()
      performAction(command)
    }

  }


}
