package medium

import scala.io.StdIn.readInt

//https://www.hackerrank.com/challenges/number-of-binary-search-tree/problem
object NumberOfBinarySearchTree {

  case class TreeNode(var value: Int, var left: TreeNode = null, var right: TreeNode = null)

  def binom(n: Int, k: Int): Int = {
    require(0 <= k && k <= n)
    @annotation.tailrec
    def binomtail(nIter: Int, kIter: Int, ac: Int): Int = {
      if (kIter > k) ac
      else binomtail(nIter + 1, kIter + 1, (nIter * ac) / kIter)
    }
    if (k == 0 || k == n) 1
    else binomtail(n - k + 1, 1, 1)
  }

  def factorial(n: Int): BigInt = {
    if(n == 0){
      1
    }else{
      n * factorial(n-1)
    }
  }

  def numberOfBinarySearchTree(N: Int): BigInt = {
    factorial(2*N) / (factorial(N + 1) * factorial(N)) % (Math.pow(10,8).toInt + 7)
  }

  def main(args: Array[String]): Unit = {
    val T = readInt()

    for(_ <- 1 to T){
      val N = readInt()
      println(numberOfBinarySearchTree(N))
    }


  }
}
