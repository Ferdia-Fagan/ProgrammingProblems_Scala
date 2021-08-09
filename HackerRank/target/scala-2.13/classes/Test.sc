import java.util


object X{
  def x(): Unit = {

    def helper(a: Array[Int]): Unit = {
      val b = a
      a.slice(2,5).copyToArray(b,4)
    }

    val a = Array(1,2,3,4,5,6,7,8,9,10)

    helper(a)

    println("a is : " + a.mkString(", "))
//    println("b is : " + b.mkString(", "))

  }

  def setLineOfMapToValue(map: Array[Array[Boolean]],
                          row_I: Int,
                          col_StartI: Int, col_EndI: Int, value: Boolean): Array[Array[Boolean]] = {
    util.Arrays.fill(map(row_I), col_StartI, col_EndI, value)
    map
  }
}

var currentMap = Array.fill(5)(Array.fill(5)(false))

val x = X.setLineOfMapToValue(currentMap,
                      1,
              1, 2,
                      true)

println("currentMap: " + currentMap.map(_.mkString(", ")).mkString("\n"))
println("x: " + x.map(_.mkString(", ")).mkString("\n"))

for(n <- 1 to 5){
  println(n)
}
