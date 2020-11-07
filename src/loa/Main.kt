package loa

import loa.minmax.MinMaxAI

var isBlackAI = false
var isWhiteAI = true
val black = BoardPosition.ItemType.B
val white = BoardPosition.ItemType.W

var board = State()
var turn = 0

fun main() {
    board.initDefaultBoard()
    board.printBoard()


    while (true) {
        println("Select Black Player's Type:\n1.Human \n2.AI")
        val input = readLine()
        when(input?.trim()) {
            "1" -> {
                isBlackAI = false
                break
            }
            "2" -> {
                isBlackAI = true
                break
            }
            else -> {
                println("Invalid Option")
            }
        }
    }

    while (true) {
        println("Select White Player's Type:\n1.Human \n2.AI")
        val input = readLine()
        when(input?.trim()) {
            "1" -> {
                isWhiteAI = false
                break
            }
            "2" -> {
                isWhiteAI = true
                break
            }
            else -> {
                println("Invalid Option")
            }
        }
    }


    while (true) {

        println("1. Get Checker Counts  \n2. ApplyMove \n3. PrintBoard")

        val input = if (isBlackAI && isWhiteAI) "2" else readLine()

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
                    // player1
                    println("BLACK's Turn")

                    val res = if (isBlackAI) aiMove(black) else userMove(black)

                    if (res) {
                        println("GAME-OVER")
                        break
                    }
                } else {
                    // player2
                    println("WHITE's Turn")

                    val res = if (isWhiteAI) aiMove(white) else userMove(white)

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

fun aiMove(type: BoardPosition.ItemType) : Boolean {

    val minMax = MinMaxAI(
        maxDepth = Constants.MAX_DEPTH,
        root = board,
        type
    )

    val startTime = System.currentTimeMillis()
    println("A.I is thinking !")
    val result = minMax.getMinMaxAIMove()
    val endTime = System.currentTimeMillis()
    println("Time Taken : ${(endTime-startTime)/1000} s")

    board = result
    board.printBoard()
    turn++

    if (minMax.isGameOver(board)) {
        println("!! GAMEOVER !!")
        return true
    }

    return false
}

fun userMove(type: BoardPosition.ItemType) : Boolean {

    println("Select Checker: \nFormat:- r:c")

    val coords = readLine()?.split(':')
    if (coords.isNullOrEmpty()) return false

    val row = coords[0].toInt()
    val col = coords[1].toInt()

    val bp = board.board[row]!![col]
    if (bp.item == BoardPosition.ItemType.W && turn % 2 == 0) {
        println("Please Select a BLACK Checker")
        return false
    }
    else if (bp.item == BoardPosition.ItemType.B && turn % 2 != 0) {
        println("Please Select a WHITE Checker")
        return false
    }
    else if (bp.item == BoardPosition.ItemType.E) {
        println("Selected Board Position EMPTY !!")
        return false
    }

    val moves = bp.getValidMoves()
    if (!moves.isNullOrEmpty()) println("Select Move:")
    else {
        println("No Valid Moves")
        return false
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
            board = clone
            board.printBoard()
            turn++

            val minMax = MinMaxAI(
                maxDepth = Constants.MAX_DEPTH,
                root = board,
                type
            )
            if (minMax.isGameOver(board)) {
                println("!! GAMEOVER !!")
                return true
            }
        } else {
            println("Invalid Move")
        }
    }

    return false


}