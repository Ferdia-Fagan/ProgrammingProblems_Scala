trait Node
case class EmptyTree() extends Node
case class TreeNode(value: Int, var left: TreeNode = null, var right: TreeNode = null) extends Node

val x = new TreeNode(10)
val y = new TreeNode(20)
val z = new TreeNode(30)
x.left = y
println(x.left)
x.left = z
println(x.left)


