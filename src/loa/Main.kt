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

        val input = readLine()

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
                    if (ai == BoardPosition.ItemType.B) aiMove()
                    else userMove()
                } else {
                    println("WHITE's Turn")
                    if (ai == BoardPosition.ItemType.W) aiMove()
                    else userMove()
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

fun aiMove() {
    val minMax = MinMaxAI(
        maxDepth = 3,
        root = board,
        ai
    )
    println("A.I is thinking !")
    val result = minMax.getMinMaxAIMove()
    board = result
    board.printBoard()
    turn++
}

fun userMove() {
    println("Select Checker: \nFormat:- r:c")

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

            /*clone.printBoard()
            println("Before Applying move")
            init.printBoard()*/
            board = clone
            board.printBoard()
            turn++
        } else {
            println("Invalid Move")
        }
    }

}