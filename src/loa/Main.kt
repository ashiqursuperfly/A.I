package loa


val player = BoardPosition.ItemType.W
val ai = BoardPosition.ItemType.B
var board = State()
var turn = 0

fun main() {
    board.initDefaultBoard()
    board.printBoard()

    while (true) {

        println("1. Get Checker Counts  \n2. ApplyMove \n3. PrintBoard")

        val input = "2"

        when (input?.trim()) {
            "1" -> {
                println("Format:- row:col")
                val coords = readLine()?.split(':')
                if (coords.isNullOrEmpty()) return

                val row = coords[0].toInt()
                val col = coords[1].toInt()

                println("H: ${board.board[row]!![col].horizontal.checkerCount}")
                println("V: ${board.board[row]!![col].vertical.checkerCount}")
                println("TL_BR: ${board.board[row]!![col].topLeftToBottomRight.checkerCount}")
                println("BL_TR: ${board.board[row]!![col].bottomLeftToTopRight.checkerCount}")

            }
            "2" -> {
                if (turn % 2 == 0) {
                    println("BLACK's Turn")
                    var res = false
                    if (ai == BoardPosition.ItemType.B) res = aiMove()
                    else res = userMove()

                    if (res) {
                        println("GAME-OVER")
                        break
                    }
                } else {
                    println("WHITE's Turn")
                    var res = false
                    if (ai == BoardPosition.ItemType.W) res = aiMove()
                    else res = userMove()

                    if (res) {
                        println("GAME-OVER")
                        break
                    }
                }
            }
            "3" -> {
                board.printBoard()
            }
            else -> {
                println("Invalid Option")
            }
        }
    }
}

fun aiMove() : Boolean {
    val minMax = MinMaxAI(
        maxDepth = 2,
        root = board,
        ai
    )
    if (minMax.isGameOver(board)) {
        println("$player WINS")
        return true
    }
    val startTime = System.currentTimeMillis()
    println("A.I is thinking !")
    val result = minMax.getMinMaxAIMove()
    val endTime = System.currentTimeMillis()
    println("Time Taken : ${(endTime-startTime)/1000} s")
    board = result
    board.printBoard()
    turn++

    if (minMax.isGameOver(board)) {
        println("$ai WINS")
        return true
    }

    return false

}

fun userMove() : Boolean {
    val minMax = MinMaxAI(
        maxDepth = 2,
        root = board,
        player
    )
    if (minMax.isGameOver(board)) {
        println("$ai WINS")
        return true
    }
    println("A.I is thinking !")
    val startTime = System.currentTimeMillis()
    val result = minMax.getMinMaxAIMove()
    val endTime = System.currentTimeMillis()
    println("Time Taken : ${(endTime-startTime)/1000} s")
    board = result
    board.printBoard()
    turn++
    if (minMax.isGameOver(board)) {
        println("$player WINS")
        return true
    }
    return false

    /*println("Select Checker: \nFormat:- r:c")

    val coords = readLine()?.split(':')
    if (coords.isNullOrEmpty()) return

    val row = coords[0].toInt()
    val col = coords[1].toInt()


    val bp = board.board[row]!![col]
    if (bp.item == BoardPosition.ItemType.W && turn % 2 == 0) {
        println("Please Select a BLACK Checker")
        return
    }
    else if (bp.item == BoardPosition.ItemType.B && turn % 2 != 0) {
        println("Please Select a WHITE Checker")
        return
    }
    else if (bp.item == BoardPosition.ItemType.E) {
        println("Selected Board Position EMPTY !!")
        return
    }

    val moves = bp.getValidMoves()
    if (!moves.isNullOrEmpty()) println("Select Move:")
    else {
        println("No Valid Moves")
        return
    }
    for (item in moves.withIndex()) {
        println("${item.index}. ${moves[item.index]}")
    }

    val selected = readLine()
    if (!selected.isNullOrBlank()) {
        val moveIdx = selected.toInt()
        val clone = board.getClone()
        val r = clone.applyMove(moves[moveIdx])
        if (r) {
            println("After Applying move:$moveIdx")

            *//*clone.printBoard()
            println("Before Applying move")
            init.printBoard()*//*
            board = clone
            board.printBoard()
            turn++
        } else {
            println("Invalid Move")
        }
    }*/


}