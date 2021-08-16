package hard


import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn.readLine
import scala.math.Ordering.BooleanOrdering

sealed trait DirectionOfWordSlot
//case object Horizontal extends DirectionOfWordSlot
//case object Vertical extends DirectionOfWordSlot
//case object HorizontalAndVertical extends DirectionOfWordSlot
case object LeftStartToRightEnd_WordSlot extends DirectionOfWordSlot
case object TopStartToBottomEnd_WordSlot extends DirectionOfWordSlot
case object NoDirection_WordSlot extends DirectionOfWordSlot


sealed trait BoardSlotValue
case object WallBlock extends BoardSlotValue
case class OpenEntrySlot(direction: DirectionOfWordSlot) extends BoardSlotValue
case class FilledEntrySlot(filledValue: Char,direction: DirectionOfWordSlot) extends BoardSlotValue

case class WordSlot(start_x: Int, start_y: Int, end_x: Int, end_y: Int,
                    numOfCharacters: Int, directionOfWordSlot: DirectionOfWordSlot,
                    var filled: Boolean)

case class SolutionWord(solutionWord: String, var wordUsed: Boolean)


// https://www.hackerrank.com/challenges/crosswords-101/problem
object BoardSolvingAttempt {

  def checkIfWordFitsInWordSlot(board: Array[Array[Char]],
                                wordSlot: WordSlot, solutionWord: String): Boolean = {

    if(wordSlot.numOfCharacters == solutionWord.length &&
      wordSlot.directionOfWordSlot == LeftStartToRightEnd_WordSlot){
      solutionWord.zip(wordSlot.start_x to wordSlot.end_x).foreach {
        case (wordSlot_Char, boardXPos) => {
//          if(board(wordSlot.start_y)(boardXPos) == OpenEntrySymbol || board(wordSlot.start_y)(boardXPos) == wordSlot_Char){
//            true
//          }else{
//            false
//          }
          if(board(wordSlot.start_y)(boardXPos) != OpenEntrySymbol && board(wordSlot.start_y)(boardXPos) != wordSlot_Char){
            return false
          }
        }
      }
      return true
    }
    else if(wordSlot.numOfCharacters == solutionWord.length &&
      wordSlot.directionOfWordSlot == TopStartToBottomEnd_WordSlot){
      solutionWord.zip(wordSlot.start_y to wordSlot.end_y).foreach {
        case (wordSlot_Char, boardYPos) => {
          if(board(boardYPos)(wordSlot.start_x) != OpenEntrySymbol && board(boardYPos)(wordSlot.start_x) != wordSlot_Char){
            return false
          }
        }
      }
      return true
    }
    else {
      false
    }
  }

  def fillInBoardWordSlotWithWord(board: Array[Array[Char]],
                                  solutionWord: String, wordSlot: WordSlot): Array[Array[Char]] = {
    if(wordSlot.directionOfWordSlot == LeftStartToRightEnd_WordSlot){
      solutionWord.zip(wordSlot.start_x to wordSlot.end_x)
        .foreach{ case (solutionWord_Char, board_XPos) =>
          board(wordSlot.start_y)(board_XPos) = solutionWord_Char
        }
    } else {
      solutionWord.zip(wordSlot.start_y to wordSlot.end_y)
        .foreach { case (solutionWord_Char, board_YPos) =>
          board(board_YPos)(wordSlot.start_x) = solutionWord_Char
        }
    }
    board
  }

  def deepCopyOfBoard(board: Array[Array[Char]]): Array[Array[Char]] =
    board.map(_.clone())

  def removeWordFromSolutionWordsWithIndex(theSolutionWords: Array[String], i: Int): Array[String] = {
    theSolutionWords.take(i) ++ theSolutionWords.drop(i + 1)
  }

  def solveBoard(theBoard: Array[Array[Char]],
                 theWordSlots: List[WordSlot], theSolutionWords: Array[String]): Array[Array[Char]] = {


    def helper(board: Array[Array[Char]],
               wordSlots: List[WordSlot], solutionWords: Array[String]): (Array[Array[Char]], Boolean) = {
      if(wordSlots.nonEmpty){
        val (matchingSolutionWords_With_OGIndex) = solutionWords.zipWithIndex
          .filter{ case (solutionWord,_) =>
            (
              solutionWord.length == wordSlots.head.numOfCharacters
                &&
                checkIfWordFitsInWordSlot(board, wordSlots.head, solutionWord)
              )
          }

        //        val filteredSolutionWordsThatDoNotFitSlot = solutionWords.zipWithIndex
        //          .filterNot{ case (_,index) => sizeMatchingSolutionIndexs.contains(index)}
        //          .map(_._1)

        if(matchingSolutionWords_With_OGIndex.isEmpty){
          (board,true)
        } else {
          var i = 0
          var keepGoing = true

          while(i < matchingSolutionWords_With_OGIndex.length && keepGoing){
            val (solutionWord,solutionWord_OGIndex) = matchingSolutionWords_With_OGIndex(i)

            val boardDuplicate = deepCopyOfBoard(board)

            val boardWithEntryAdjustment = fillInBoardWordSlotWithWord(boardDuplicate,
              solutionWord, wordSlots.head)

            val (completedBoard,keepItGoing) = helper(boardWithEntryAdjustment,
              wordSlots.tail,
              removeWordFromSolutionWordsWithIndex(solutionWords, solutionWord_OGIndex)
            )
            if(!keepItGoing){
              return (completedBoard, keepItGoing)
            }
            keepGoing = keepItGoing
            i += 1
          }
          return (board, true)
        }



        //        matchingSolutionWords_With_OGIndex.foreach{ case (solutionWord,solutionWord_OGIndex) => {
        //          val boardDuplicate = deepCopyOfBoard(board)
        //
        //          val boardWithEntryAdjustment = fillInBoardWordSlotWithWord(boardDuplicate,
        //                                                                      solutionWord, wordSlots.head)
        //
        //          helper(boardWithEntryAdjustment,
        //            wordSlots.tail,
        //            removeWordFromSolutionWordsWithIndex(solutionWords, solutionWord_OGIndex)
        //          )
        //        }}



      } else {
        (board,false)
      }
    }

    helper(theBoard, theWordSlots, theSolutionWords)._1
  }

  /**
  Solution:

    loop through board
      downwards
        left to right



   **/

  val WallBlockSymbol = '+'
  val OpenEntrySymbol = '-'

  val SIZE = 9

  def checkIfWordSpaceIsHorizontal(x: Int, y: Int, board: Array[Array[Char]]): Boolean = {
    if (x == 0){
      if(board(y)(x - 1) == OpenEntrySymbol){
        return true
      }
    } else if (x == SIZE){
      if(board(y)(x + 1) == OpenEntrySymbol){
        return true
      }
    } else{
      if( (board(y)(x - 1) == OpenEntrySymbol) || board(x + 1)(y) == OpenEntrySymbol){
        return true
      }
    }

    return false
  }

  def checkIfWordSpaceIsVertical(x: Int, y: Int, board: Array[Array[Char]]): Boolean = {
    if (y == 0){
      if(board(y - 1)(x) == OpenEntrySymbol){
        return true
      }
    } else if (y == SIZE){
      if(board(y + 1)(x) == OpenEntrySymbol){
        return true
      }
    } else{
      if( (board(y - 1)(x) == OpenEntrySymbol) || board(x)(y + 1) == OpenEntrySymbol){
        return true
      }
    }

    return false
  }

  def getLengthOfHorizontalWordSlot(row: Array[Char], startXPosOfWordSlot: Int): Int = {
    val lengthOfWordSlot = row.drop(startXPosOfWordSlot).indexOf(WallBlockSymbol)
    if(lengthOfWordSlot != -1){
      lengthOfWordSlot
    } else {
      row.length - startXPosOfWordSlot
    }
  }

  def getIfRightDirectionalWordSlot(x: Int, y: Int, board: Array[Array[Char]]): Option[WordSlot] = {
    val a = (x == 0) || (board(y)(x - 1) == WallBlockSymbol )
    val b = (x != SIZE) && board(y)(x + 1) == OpenEntrySymbol
    val c = a && b
    if(c){
      val lengthOfWordSlot = getLengthOfHorizontalWordSlot(board(y),x)
      Some(
        WordSlot(
          x,y,
          lengthOfWordSlot-1 + x,y,
          lengthOfWordSlot, LeftStartToRightEnd_WordSlot,
          filled = false
        )
      )
    }else{
      None
    }
  }

  def getIfDownDirectionalWordSlot(x: Int, y: Int, board: Array[Array[Char]]): Option[WordSlot] = {
    val a = (y == 0) || (board(y - 1)(x) == WallBlockSymbol)
    val b = (y != SIZE) && board(y + 1)(x) == OpenEntrySymbol
    val c = a && b
    if(c){
      var endOfDownWordSlot_Y = y + 1

      while ((endOfDownWordSlot_Y != SIZE + 1) && board(endOfDownWordSlot_Y)(x) == OpenEntrySymbol) {
        endOfDownWordSlot_Y+=1
      }
      endOfDownWordSlot_Y-=1
      Some(
        WordSlot(
          x, y,
          x, endOfDownWordSlot_Y,
          endOfDownWordSlot_Y - y + 1, TopStartToBottomEnd_WordSlot,
          filled = false
        )
      )
    }else{
      None
    }
  }

  def solve(boardAsCharacters: Array[Array[Char]], solutionWords: Array[String]): Array[Array[Char]]  = {

    val wordSlots = ArrayBuffer[WordSlot]()

    for (y <- 0 to SIZE) {
      for (x <- 0 to SIZE) {
        if(boardAsCharacters(y)(x) == OpenEntrySymbol){
          val downDirectionWordSlot = getIfDownDirectionalWordSlot(x, y, boardAsCharacters)
          val rightDirectionWordSlot = getIfRightDirectionalWordSlot(x, y, boardAsCharacters)

          if (rightDirectionWordSlot.nonEmpty) {
            wordSlots += rightDirectionWordSlot.head
          }
          if (downDirectionWordSlot.nonEmpty) {
            wordSlots += downDirectionWordSlot.head
          }
        }
      }
    }

    solveBoard(boardAsCharacters, wordSlots.toList, solutionWords)



  }

  def main(args: Array[String]): Unit = {
    val board = (for(_ <- 1 to 10) yield {
      readLine().toCharArray
    }).toArray

    val solutionWords: Array[String] = readLine().split(";")

    println(
      solve(board, solutionWords).map(_.mkString("")).mkString("\n")
    )

  }

}

// https://www.hackerrank.com/challenges/crosswords-101/problem
//object CrosswordSolver {
//
//  def checkIfWordFitsInWordSlot(board: Array[Array[Char]],
//                                wordSlot: WordSlot, solutionWord: String): Boolean = {
//
//    if(wordSlot.numOfCharacters == solutionWord.length &&
//      wordSlot.directionOfWordSlot == LeftStartToRightEnd_WordSlot){
//      solutionWord.zip(wordSlot.start_x to wordSlot.end_x).foldLeft(true) {
//        case (_, (wordSlot_Char, boardXPos)) => {
//          if(board(wordSlot.start_y)(boardXPos) == OpenEntrySymbol || board(wordSlot.start_y)(boardXPos) == wordSlot_Char){
//            true
//          }else{
//            false
//          }
//        }
//      }
//    }
//    else if(wordSlot.numOfCharacters == solutionWord.length &&
//      wordSlot.directionOfWordSlot == TopStartToBottomEnd_WordSlot){
//      solutionWord.zip(wordSlot.start_y to wordSlot.end_y).foldLeft(true) {
//        case (_, (wordSlot_Char, boardYPos)) => {
//          if(board(boardYPos)(wordSlot.start_x) == OpenEntrySymbol || board(boardYPos)(wordSlot.start_x) == wordSlot_Char){
//            true
//          }else{
//            false
//          }
//        }
//      }
//    }
//    else {
//      false
//    }
//  }
//
//  def fillInBoardWordSlotWithWord(board: Array[Array[Char]],
//                                  solutionWord: String, wordSlot: WordSlot): Array[Array[Char]] = {
//    if(wordSlot.directionOfWordSlot == LeftStartToRightEnd_WordSlot){
//      solutionWord.zip(wordSlot.start_x to wordSlot.end_x)
//        .foreach{ case (solutionWord_Char, board_XPos) =>
//          board(wordSlot.start_y)(board_XPos) = solutionWord_Char
//        }
//    } else {
//      solutionWord.zip(wordSlot.start_y to wordSlot.end_y)
//        .foreach { case (solutionWord_Char, board_YPos) =>
//          board(board_YPos)(wordSlot.start_x) = solutionWord_Char
//        }
//    }
//    board
//  }
//
//  def deepCopyOfBoard(board: Array[Array[Char]]): Array[Array[Char]] =
//    board.map(_.clone())
//
//  def removeWordFromSolutionWordsWithIndex(theSolutionWords: Array[String], i: Int): Array[String] = {
//    theSolutionWords.take(i) ++ theSolutionWords.drop(i + 1)
//  }
//
//  def solveBoard(theBoard: Array[Array[Char]],
//                 theWordSlots: List[WordSlot], theSolutionWords: Array[String]): Array[Array[Char]] = {
//
//
//    def helper(board: Array[Array[Char]],
//               wordSlots: List[WordSlot], solutionWords: Array[String]): (Array[Array[Char]], Boolean) = {
//      if(wordSlots.nonEmpty){
//        val (matchingSolutionWords_With_OGIndex) = solutionWords.zipWithIndex
//          .filter{ case (solutionWord,_) =>
//            (
//              solutionWord.length == wordSlots.head.numOfCharacters
//                &&
//              checkIfWordFitsInWordSlot(board, wordSlots.head, solutionWord)
//            )
//        }
//
////        val filteredSolutionWordsThatDoNotFitSlot = solutionWords.zipWithIndex
////          .filterNot{ case (_,index) => sizeMatchingSolutionIndexs.contains(index)}
////          .map(_._1)
//
//        if(matchingSolutionWords_With_OGIndex.isEmpty){
//          (board,true)
//        } else {
//          var i = 0
//          var keepGoing = true
//
//          while(i < matchingSolutionWords_With_OGIndex.length && keepGoing){
//            val (solutionWord,solutionWord_OGIndex) = matchingSolutionWords_With_OGIndex(i)
//
//            val boardDuplicate = deepCopyOfBoard(board)
//
//            val boardWithEntryAdjustment = fillInBoardWordSlotWithWord(boardDuplicate,
//              solutionWord, wordSlots.head)
//
//            val (completedBoard,keepItGoing) = helper(boardWithEntryAdjustment,
//              wordSlots.tail,
//              removeWordFromSolutionWordsWithIndex(solutionWords, solutionWord_OGIndex)
//            )
//            if(!keepItGoing){
//              return (completedBoard, keepItGoing)
//            }
//            keepGoing = keepItGoing
//            i += 1
//          }
//          return (board, keepGoing)
//        }
//
//
//
////        matchingSolutionWords_With_OGIndex.foreach{ case (solutionWord,solutionWord_OGIndex) => {
////          val boardDuplicate = deepCopyOfBoard(board)
////
////          val boardWithEntryAdjustment = fillInBoardWordSlotWithWord(boardDuplicate,
////                                                                      solutionWord, wordSlots.head)
////
////          helper(boardWithEntryAdjustment,
////            wordSlots.tail,
////            removeWordFromSolutionWordsWithIndex(solutionWords, solutionWord_OGIndex)
////          )
////        }}
//
//
//
//      } else {
//        (board,false)
//      }
//    }
//
//    helper(theBoard, theWordSlots, theSolutionWords)._1
//  }
//
//  /**
//   Solution:
//
//    loop through board
//      downwards
//        left to right
//
//
//
//   **/
//
//  val WallBlockSymbol = '+'
//  val OpenEntrySymbol = '-'
//
//  val SIZE = 9
//
//  def checkIfWordSpaceIsHorizontal(x: Int, y: Int, board: Array[Array[Char]]): Boolean = {
//    if (x == 0){
//      if(board(y)(x - 1) == OpenEntrySymbol){
//        return true
//      }
//    } else if (x == SIZE){
//      if(board(y)(x + 1) == OpenEntrySymbol){
//        return true
//      }
//    } else{
//      if( (board(y)(x - 1) == OpenEntrySymbol) || board(x + 1)(y) == OpenEntrySymbol){
//        return true
//      }
//    }
//
//    return false
//  }
//
//  def checkIfWordSpaceIsVertical(x: Int, y: Int, board: Array[Array[Char]]): Boolean = {
//    if (y == 0){
//      if(board(y - 1)(x) == OpenEntrySymbol){
//        return true
//      }
//    } else if (y == SIZE){
//      if(board(y + 1)(x) == OpenEntrySymbol){
//        return true
//      }
//    } else{
//      if( (board(y - 1)(x) == OpenEntrySymbol) || board(x)(y + 1) == OpenEntrySymbol){
//        return true
//      }
//    }
//
//    return false
//  }
//
//  def getIfRightDirectionalWordSlot(x: Int, y: Int, board: Array[Array[Char]]): Option[WordSlot] = {
//    val a = (x == 0) || (board(y)(x - 1) == WallBlockSymbol )
//    val b = ((x != SIZE) && board(y)(x + 1) == OpenEntrySymbol)
//    val c = a && b
//    if(c){
//      val lengthOfWordSlot = board(y).drop(x).indexOf(WallBlockSymbol)
//      Some(
//        WordSlot(
//          x,y,
//          lengthOfWordSlot-1 + x,y,
//          lengthOfWordSlot, LeftStartToRightEnd_WordSlot,
//          filled = false
//        )
//      )
//    }else{
//      None
//    }
//  }
//
//  def getIfDownDirectionalWordSlot(x: Int, y: Int, board: Array[Array[Char]]): Option[WordSlot] = {
//    val a = (y == 0) || (board(y - 1)(x) == WallBlockSymbol)
//    val b = ((y != SIZE) && board(y + 1)(x) == OpenEntrySymbol)
//    val c = a && b
//    if(c){
//      var endOfDownWordSlot_Y = y + 1
//
//      while ((endOfDownWordSlot_Y != SIZE + 1) && board(endOfDownWordSlot_Y)(x) == OpenEntrySymbol) {
//        endOfDownWordSlot_Y+=1
//      }
//      endOfDownWordSlot_Y-=1
//      Some(
//        WordSlot(
//          x, y,
//          x, endOfDownWordSlot_Y,
//          endOfDownWordSlot_Y - y + 1, TopStartToBottomEnd_WordSlot,
//          filled = false
//        )
//      )
//    }else{
//      None
//    }
//  }
//
////  def getIfDownDirectionalWordSlot(x: Int, y: Int, board: Array[Array[Char]]): Option[WordSlot] = {
////Option[WordSlot]
////  }
////
////  def getDirectionOfWordSlot(x: Int, y: Int, board: Array[Array[Char]]): DirectionOfWordSlot = {
////
////    val rightDirection =
////      (x != SIZE) && (board(y)(x - 1) == WallBlockSymbol && board(y)(x + 1) == OpenEntrySymbol)
////
////    val downDirection =
////      (y != SIZE) && (board(y - 1)(x) == WallBlockSymbol && board(y + 1)(x) == OpenEntrySymbol)
////
////    if (x != SIZE) {
////      board(x)(y + 1) == OpenEntrySymbol
////    } else {
////      if(){
////
////      }
////    }
////  }
////
////  def getDirectionOfWordSpaceOfTile(x: Int, y: Int, board: Array[Array[Char]]): DirectionOfWordSlot = {
////    (checkIfWordSpaceIsHorizontal(x,y,board),checkIfWordSpaceIsVertical(x,y,board)) match {
//////      case (true, true) => HorizontalAndVertical
////      case (true, false) => Horizontal
////      case (false, true) => Vertical
////    }
////  }
////
////  def evaluate(boardTileValue: Char, x: Int, y: Int, board: Array[Array[Char]]): BoardSlotValue = {
////    if(boardTileValue == WallBlockSymbol){
////      WallBlock
////    } else {
////      // therefor is word slot
////      val directionOfWordSlot = getDirectionOfWordSpaceOfTile(x, y, board)
////      OpenEntrySlot(directionOfWordSlot)
////    }
////  }
//
//  def solve(boardAsCharacters: Array[Array[Char]], solutionWords: Array[String]): Array[Array[Char]]  = {
//
//    val wordSlots = ArrayBuffer[WordSlot]()
//
//    for (y <- 0 to SIZE) {
//      for (x <- 0 to SIZE) {
//        if(boardAsCharacters(y)(x) == OpenEntrySymbol){
//          val downDirectionWordSlot = getIfDownDirectionalWordSlot(x, y, boardAsCharacters)
//          val rightDirectionWordSlot = getIfRightDirectionalWordSlot(x, y, boardAsCharacters)
//
//          if (rightDirectionWordSlot.nonEmpty) {
//            wordSlots += rightDirectionWordSlot.head
//          }
//          if (downDirectionWordSlot.nonEmpty) {
//            wordSlots += downDirectionWordSlot.head
//          }
//        }
//      }
//    }
//
//    solveBoard(boardAsCharacters, wordSlots.toList, solutionWords)
//
//
//
//  }
//
//
//  def main(args: Array[String]): Unit = {
//    val board = (for(_ <- 1 to 10) yield {
//      readLine().toCharArray
//    }).toArray
//
//    val solutionWords: Array[String] = readLine().split(";")
//
//    println(
//      solve(board, solutionWords).map(_.mkString(" ")).mkString("\n")
//    )
//
//  }
//
//}
