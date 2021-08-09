package easy

import scala.io.StdIn.readInt

object PascalsTriangle {

  def pascalsTriangle(k: Int): Unit = {
    def factorial(n: Int): Int = {
      if (n == 0) 1
      else n * factorial(n - 1)
    }

    def calc(n: Int, r: Int): Int = {
      factorial(n) / (factorial(r) * factorial(n - r))
    }

    def helper(n: Int): Unit = {
      if (n != k) {
        for (r <- 0 to n) {
          print(calc(n, r) + " ")
        }
        print("\n")
        helper(n + 1)
      }
    }

    helper(0)
  }

  def main(args: Array[String]): Unit = {
    pascalsTriangle(readInt())
  }

}
