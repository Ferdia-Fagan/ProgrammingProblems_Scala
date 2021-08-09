package medium

import scala.annotation.tailrec
import scala.io.StdIn.{readInt, readLine}

// https://www.hackerrank.com/challenges/password-cracker-fp/problem
object PasswordCrackerFP {

//  def passwordCrackerFP_BySplittingAttemptedPassword(passwords: Set[String], attemptedPassword: String): Unit = {

  def passwordCrackerFP_BySplittingAttemptedPassword(passwords: Set[String], attemptedPassword: String): Unit = {
    @tailrec
    def helper(startCutIndex: Int, endCutIndex: Int, currentCol: List[String] = List[String]()): List[List[String]] = {
      val currentSegment = attemptedPassword.slice(startCutIndex,endCutIndex)
      if(passwords.contains(currentSegment)){
        if(endCutIndex < attemptedPassword.length){
          return helper(endCutIndex,endCutIndex + 1,currentSegment :: currentCol)
        }else{
          return List(currentSegment :: currentCol)
        }
      }else{
        if(endCutIndex >= attemptedPassword.length){
          if(startCutIndex < attemptedPassword.length-1){
            return helper(startCutIndex + 1,startCutIndex + 2,currentCol)
          }else{
//            return List(currentCol)
            return List()
          }
        }else {
          return helper(startCutIndex,endCutIndex + 1,currentCol)
        }
      }
    }
    val results = helper(0,1)
      .filter(el =>
      el.map(_.length).sum == attemptedPassword.length
    )

    if(results.isEmpty){
      println("WRONG PASSWORD")
    }else{
      results.foreach(el => {
        println(el.reverse.mkString(" "))
      })
    }
  }

  def main(args: Array[String]): Unit = {
    val T = readInt()

    for(_ <- 1 to T){
      val N = readInt()
      val passwords = readLine().split(" ").toSet
      val attempt = readLine()

      passwordCrackerFP_BySplittingAttemptedPassword(passwords, attempt)

//      println(passwordCrackerFP(passwords, attempt).map(_.mkString(" ")).mkString(" "))
    }

  }

}
