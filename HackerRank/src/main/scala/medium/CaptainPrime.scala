package medium

import scala.io.StdIn.readInt
import scala.util.control.Breaks.break

object CaptainPrime {

  def isPrime(num: Int): Boolean = {
    if(num < 2){
      return false
    }else{
      !((2 until num - 1) exists (num % _ == 0))
    }

  }

  def numContainsAZero(num: String): Boolean =
    num.contains('0')

  trait Fate
  case class Central(num: Int) extends Fate
  case class Left(num: Int) extends Fate
  case class Right(num: Int) extends Fate
  case class Dead() extends Fate


  def captainPrime(l: List[Int]): Unit = {

    def assignFate(num: Int): Fate = {
      val numAsStr = num.toString
      if(isPrime(num) && !numContainsAZero(numAsStr)){
        if(numAsStr.length == 1){
          return Central(num)
        }else{
          val rightFate: Boolean = numAsStr.take(numAsStr.length-1).tail.foldLeft(List[String](numAsStr.head.toString))((a,b) => {
//            (s"${b}${a.head}") :: a
            ( a.head + b.toString) :: a
          }).forall(a => isPrime(a.toInt))

          val numAsStr_Reversed = numAsStr.reverse
          val leftFate: Boolean = numAsStr_Reversed.take(numAsStr_Reversed.length-1).tail.foldLeft(List[String](numAsStr_Reversed.head.toString))((a,b) => {
            //            (s"${b}${a.head}") :: a
            ( b.toString + a.head) :: a
          }).forall(a => isPrime(a.toInt))

          if(leftFate && rightFate){
            return Central(num)
          }else if(leftFate){
            return Left(num)
          }else if(rightFate){
            return Right(num)
          }else{
            return Dead()
          }
        }

      }else{
        return Dead()
      }
    }

    val x = l.map(assignFate)
    x.foreach(a => println(a.getClass.getSimpleName.toString.toUpperCase()))
  }

  def main(args: Array[String]): Unit = {
    val T = readInt()

    val l = (for(_ <- 1 to T) yield {
      readInt()
    }).toList

    captainPrime(l)

  }
}
