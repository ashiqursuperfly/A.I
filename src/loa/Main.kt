package loa

fun main() {
    val init = State()
    init.printBoard()

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

                println("H: ${init.board[row][col].horizontal.checkerCount}")
                println("V: ${init.board[row][col].vertical.checkerCount}")
                println("TL_BR: ${init.board[row][col].topLeftToBottomRight.checkerCount}")
                println("BL_TR: ${init.board[row][col].bottomLeftToTopRight.checkerCount}")

            }
            "2" -> {
                println("Format:- (W|B)-startRow:startCol-endRow:endCol")

                val move = readLine()?.split('-')
                if (move.isNullOrEmpty()) return

                val player = move[0].trim()

                val startPos = move[1].split(':')
                val endPos = move[2].split(':')

                val sr = startPos[0].toInt()
                val sc = startPos[1].toInt()

                val fr = endPos[0].toInt()
                val fc = endPos[1].toInt()

                val m = Move(
                    playerType = if (player == "W") BoardPosition.ItemType.W else BoardPosition.ItemType.B,
                    startCoord = Pair(sr, sc),
                    endCoord = Pair(fr, fc)
                )
                val r = init.applyMove(m)
                if (r) {
                    println("After Applying move:$m")
                    init.printBoard()
                } else {
                    println("Invalid Move")
                }

            }
            "3" -> {
                init.printBoard()
            }
            else -> {
                println("Invalid Option")
            }
        }



    }

}