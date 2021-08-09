package easy

import scala.annotation.tailrec
import scala.io.StdIn.readInt

object FibonacciNumbers {

  def fibonacci2(x: Int): Int = {
    if (x < 2) x else fibonacci2(x - 1) + fibonacci2(x - 2)
  }

  def fibonacci(x: Int): Int = {
    if (x <= 2) {
      x - 1
    } else {
      @tailrec
      def helper(f_n2: Int, f_n1: Int, n: Int): Int = n match {
        case 1 => f_n1
        case _ => helper(f_n1, f_n2 + f_n1, n - 1)
      }

      helper(0, 1, x - 1)
    }
  }

  def main(args: Array[String]) {
    /** This will handle the input and output* */
    println(fibonacci(readInt()))

  }

}
