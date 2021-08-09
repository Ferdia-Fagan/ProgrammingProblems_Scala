package easy

import scala.io.StdIn.{readInt, readLine}

object MissingNumbers {

  def missingNumbers(n: Int, n_l: List[Int],
                     m: Int, m_l: List[Int]): List[Int] = {
    def reduceThis(x_l: List[Int]): Map[Int, Int] = {
      x_l.foldLeft(Map[Int, Int]().withDefaultValue(0))((x_l_count, x_l_element) => {
        x_l_count + (x_l_element -> (x_l_count(x_l_element) + 1))
      })
    }

    val n_l_count = reduceThis(n_l)
    val m_l_count = reduceThis(m_l)

    n_l_count.foldLeft(List[Int]())((l, e) => {
      if (!m_l_count.contains(e._1) || m_l_count(e._1) != e._2) {
        e._1 :: l
      } else {
        l
      }
    }).sorted
  }

  def main(args: Array[String]) {
    /*
    Enter your code here.
    Read input from STDIN.
    Print output to STDOUT.
    Your class should be named Solution
    */
    val n = readInt()
    val n_l = readLine().trim().split(" ").map(x => x.toInt).toList

    val m = readInt()
    val m_l = readLine().trim().split(" ").map(x => x.toInt).toList

    println(missingNumbers(n, n_l, m, m_l).mkString(" "))
  }
}
