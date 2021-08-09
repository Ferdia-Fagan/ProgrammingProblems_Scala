package easy

import scala.annotation.tailrec
import scala.io.StdIn.readLine

object ComputingTheGCD {
  // https://www.hackerrank.com/challenges/functional-programming-warmups-in-recursion---gcd/problem


  def gcd(X: Int, Y: Int): Int = {
    @tailrec
    def helper(X: Int, Y: Int): Int = {
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

  //  def gcd(X: Int, Y: Int): Int =
  //  {
  //    // GCD: x = y*q + r
  //    @tailrec
  //    def helper(x: Int, y: Int):Int = {
  //      if(y != 0){
  //        val r = x % y
  //        helper(y,r)
  //      }else{
  //        x
  //      }
  //    }
  //
  //    val (x,y) = if(X>Y){(X,Y)} else (Y,X)
  //    helper(x,y)
  //  }


  /** This part handles the input/output. Do not change or modify it * */
  def acceptInputAndComputeGCD(pair: List[Int]) = {
    println(gcd(pair.head, pair.reverse.head))
  }

  def main(args: Array[String]) {
    /** The part relates to the input/output. Do not change or modify it * */
    acceptInputAndComputeGCD(readLine().trim().split(" ").map(x => x.toInt).toList)

  }

}
