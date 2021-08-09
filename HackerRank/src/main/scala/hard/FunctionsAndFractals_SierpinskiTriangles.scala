package hard

import java.util
import scala.io.StdIn.readInt


// https://www.hackerrank.com/challenges/functions-and-fractals-sierpinski-triangles/problem
object FunctionsAndFractals_SierpinskiTriangles {

  case class TriangleBase(start_ColI: Int, end_ColI: Int)

  def printMap(map: Array[Array[Boolean]]) =
    println(map.map(row => row.map(is1Value => if(is1Value) '1' else '_').mkString).mkString("\n"))

  def SierpinskiTriangles(N: Int): Unit = {

    def setLineOfMapToValue(map: Array[Array[Boolean]],
                              row_I: Int,
                              col_StartI: Int, col_EndI: Int, value: Boolean): Unit = {
      util.Arrays.fill(map(row_I), col_StartI, col_EndI, value)
    }

    def removeATriangle(map: Array[Array[Boolean]],
                        triangleTip_RowI: Int, triangleHeight: Int,
                        col_StartI: Int, col_EndI: Int): TriangleBase = {
      var colStartI = col_StartI
      var colEndI = col_EndI
      for(row_I <- triangleTip_RowI to (triangleTip_RowI - triangleHeight) by -1){
        setLineOfMapToValue(map, row_I, colStartI, colEndI, false)
        colStartI-=1
        colEndI+=1
      }
      TriangleBase(colStartI + 1,colEndI - 1)
    }

    def helper(map: Array[Array[Boolean]],
               triangleBase_RowIndex: Int,
               triangleBase_StartIndex: Int, triangleBase_EndIndex: Int,
               layersToGo: Int): Unit = {
      if(layersToGo != 0){
        val baseWidth = (triangleBase_EndIndex - triangleBase_StartIndex)/2
        val triangeBase_ColI = triangleBase_StartIndex + baseWidth
        val height = baseWidth / 2

        val triangleBase: TriangleBase = removeATriangle(map,
                        triangleBase_RowIndex, height,
                        triangeBase_ColI, triangeBase_ColI + 1)

        // left
        helper(map,
              triangleBase_RowIndex,
              triangleBase_StartIndex, triangeBase_ColI,
                layersToGo - 1)

        // right
        helper(map,
          triangleBase_RowIndex,
          triangeBase_ColI+1, triangleBase_EndIndex,
          layersToGo - 1)

        // top
        helper(map,
          triangleBase_RowIndex - height - 1,
          triangleBase.start_ColI, triangleBase.end_ColI,
          layersToGo - 1)
      }
    }

    val currentMap = Array.fill(32)(Array.fill(63)(false))
    var col_StartI = 31
    var col_EndI = 32

    for(i <- 0 to 31){
      setLineOfMapToValue(currentMap,
                          i,
                          col_StartI, col_EndI,
                          value = true)
      col_StartI-=1
      col_EndI+=1
    }

//    printMap(currentMap)

    helper(currentMap,31,0,63,N)
//    removeATriangle(currentMap,31,31/2,31,32)

    printMap(currentMap)
//    for(_ <- 1 to N){
//      currentMap = helper(currentMap, )
//    }

  }


//  def sierp(b:Array[Array[Char]], x:Int, y:Int, h:Int, w:Int, n:Int):Array[Array[Char]] = n match {
//    case 0 =>
//      for(i <- 0 until h) {
//        b(x+i)(y+w/2) = '1'
//        for(j <- 0 until i+1) {
//          b(x+i)(y+w/2-j) = '1'
//          b(x+i)(y+w/2+j) = '1'
//        }
//      }
//      return b
//    case _ =>
//      sierp(sierp(sierp(b, x, y+w/4+1, h/2, w/2, n-1), x+h/2, y+0, h/2, w/2, n-1), x+h/2, y+w/2+1, h/2, w/2, n-1)
//  }

  //This is a solution I found online ...... Mine was faster
//  def drawTriangles(N:Int) = {
//    sierp(List.fill(32)(List.fill(63)('_').toArray).toArray, 0, 0, 32, 63, N)
//      .foreach(x => println(x.mkString("")))
//  }

//  def drawTriangles(n: Int) {
//    // Pass a segment of the list of rows, which are a list of tuples, such that each tuple
//    //     represents a item from the the inorder traversal of the segment and the other
//    //     represents a item from the reverse order traversal of the same segement.  then
//    //     compare the bottom half such that if the reverse is 1 then that means we must
//    //     mark the item as an _.
//    def foldGroup( lolot: List[ List[(String, String)]]) = lolot.splitAt(lolot.length/2) match {
//      case (first, second) => first.map( lot => lot.map( t => t._1)) :::
//        second.map( lot => lot.map( t => if( t._2 == "1" ) "_" else t._1 ) )
//    }
//    // Pass a list of 2^iteration groups such that each item in the top level list is a group that
//    //      can be folded to compute the current interations representation.  Imagine folding a
//    //      paper in half.
//    def drawGroups( lolol: List[ List[ List[String]]]) = {
//      lolol.map( lol => foldGroup(
//        (lol zip lol.reverse)               //  List[ Tuple(List[String]], List[String]]) ]
//          .map( t => (t._1 zip t._2) )    //  List[ Tuple( String, String )]
//      ) ).flatten // flatten top-level: List[List[List[String]]] -> List[List[String]]
//    }

  // Another solution I found online... Mine was faster
//    // Recursive function in sequential order of iterations.
//    def drawIteration( iter: Int, lol: List[ List[String]]): List[ List[String]] = iter match {
//      case 0 => drawIteration(iter+1,  (for( r <- 0 until 32 )
//        yield ( for( c <- 0 until 63 )
//          yield if( (31 - r) <= c && c <= ( 31 + r ) ) "1" else "_" ).toList ).toList )
//      case i if i <= n => drawIteration( i+1, drawGroups( lol.grouped( // Groups of rows for segments that are folded in half
//        (lol.length / (Math.pow( 2, i - 1))).toInt ).toList ) )
//      case _ => lol // terminate..
//    }
//
//    drawIteration( 0, Nil ).map(x => println( x.mkString("")) )
//  }

  def main(args: Array[String]): Unit = {
    val N = readInt()

    SierpinskiTriangles(N)
  }

}
