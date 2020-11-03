package loa

fun main() {
    val init = State()
    init.printBoard()

    while (true) {
        val input = readLine()?.split(":")

        if (!input.isNullOrEmpty()) {
            val row = input[0].toInt()
            val col = input[1].toInt()
            println("H: ${init.board[row][col].horizontal.checkerCount}")
            println("V: ${init.board[row][col].vertical.checkerCount}")
            println("TL_BR: ${init.board[row][col].topLeftToBottomRight.checkerCount}")
            println("BL_TR: ${init.board[row][col].bottomLeftToTopRight.checkerCount}")
        }

    }

}