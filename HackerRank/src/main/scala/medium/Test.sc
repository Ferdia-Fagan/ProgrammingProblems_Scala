//
//sealed trait Direction
//case object Horizontal extends Direction
//case object Vertical extends Direction
//
//sealed trait BoardSlotValue
//case object Block extends BoardSlotValue
//case class OpenEntrySlot(direction: Direction) extends BoardSlotValue
//case class FilledEntrySlot(filledValue: Char,direction: Direction) extends BoardSlotValue
//
//
//def evaluate(x: Int): BoardSlotValue = {
//  if(x == 0){
//    Block
//  }
//  else if(x == 1){
//    OpenEntrySlot(Horizontal)
//  }
//  else {
//    FilledEntrySlot('A', Vertical)
//  }
//}
//
//val l = Array(1,2,3,4,5)
//
//val circular_WordSlots = Iterator.continually(
//  l
//).flatten
//
//val n = l.length
//
////for (i <- l.indices){
////  println(
////    circular_WordSlots.slice(i, i + n).mkString(" ")
////  )
////}
//
//circular_WordSlots.slice(0,3).mkString(" ")
//circular_WordSlots.slice(0,3).mkString(" ")

//class Rotate_L(val l: Array[Int]){
//  def getWholeArray_FromI(i: Int): Array[Int] ={
//    l.drop(i) ++ l.take(i)
//  }
//}
//
//val l = Array(1,2,3,4,5,6,7,8,9)
//
//val x = new Rotate_L(l)
//val y = x.getWholeArray_FromI(5)
//
//l.drop(3).indexOf(5)

val a = Array(1,2,3,4,5)
val b = Array(2,5,6,1,4,5)

val i = b.filter(_ == 5)

i

(for {
  i1 <- a
  i2 <- b if (i2 == i1)
} yield List(i1, i2)).mkString("\n")

//case class X(var v: Int)
//case class Y(var v: Int)
//
//val l = Array(X(1),X(1))
//
//l.mkString(" ")
//
//val l_2 = l.map(_.copy())
//
//l_2(0).v = 2
//
//l.mkString(" ")
//l_2.mkString(" ")
//
//X.getClass.getTypeName


