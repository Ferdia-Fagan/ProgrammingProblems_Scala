package medium

import scala.annotation.tailrec
import scala.io.StdIn.{readInt, readLine}

object JumpingBunnies {
  def gcd(X: BigInt, Y: BigInt): BigInt = {
    @tailrec
    def helper(X: BigInt, Y: BigInt): BigInt = {
      if (X == 0 || Y == 0) {
        X + Y
      } else {
        helper(Y, X % Y)
      }
    }

    // clean input data
    val (x, y) = if (X > Y) {
      (X, Y)
    } else (Y, X)
    // run
    helper(x, y)
  }

  /***
   * Jumping bunnies = lcm
   * lcm(a, b) = ab/gcd(a,b)
   * @param j
   */
  def jumpingBunnies(j: Array[Int]): BigInt = {
    def lcmCalc(a: BigInt,b:BigInt): BigInt = {
      /*
      The bellow may cause overflow as: (a*b)/gcd(a,b)
      val gcdOf = gcd(a,b)
      val productOf = a * b
      productOf/gcdOf

      Therefor, it seems better idea to:
      lcm(a,b) = (a/gcd(a,b))*b
       */
      (a/gcd(a, b)) * b
    }
    j.tail.foldLeft(j.head : BigInt)((a,b) => lcmCalc(a,b))
  }

  def main(args: Array[String]): Unit = {
    val N = readInt()
    val j = readLine().split(" ").map(_.toInt)

    println(jumpingBunnies(j))
  }

}
