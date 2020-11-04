package loa

fun main() {
    var init = State()
    init.initDefaultBoard()
    init.printBoard()

    var turn = 0
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

                println("H: ${init.board[row]!![col].horizontal.checkerCount}")
                println("V: ${init.board[row]!![col].vertical.checkerCount}")
                println("TL_BR: ${init.board[row]!![col].topLeftToBottomRight.checkerCount}")
                println("BL_TR: ${init.board[row]!![col].bottomLeftToTopRight.checkerCount}")

            }
            "2" -> {
                if (turn % 2 == 0) {
                    println("BLACK's Turn")
                } else {
                    println("WHITE's Turn")
                }
                println("Select Checker: \nFormat:- r:c")

                val coords = readLine()?.split(':')
                if (coords.isNullOrEmpty()) return

                val row = coords[0].toInt()
                val col = coords[1].toInt()


                val bp = init.board[row]!![col]
                if (bp.item == BoardPosition.ItemType.W && turn % 2 == 0) {
                    println("Please Select a BLACK Checker")
                    continue
                }
                else if (bp.item == BoardPosition.ItemType.B && turn % 2 != 0) {
                    println("Please Select a WHITE Checker")
                    continue
                }
                else if (bp.item == BoardPosition.ItemType.E) {
                    println("Selected Board Position EMPTY !!")
                    continue
                }

                val moves = bp.getValidMoves()
                if (!moves.isNullOrEmpty()) println("Select Move:")
                else {
                    println("No Valid Moves")
                    continue
                }
                for (item in moves.withIndex()) {
                    println("${item.index}. ${moves[item.index]}")
                }

                val selected = readLine()
                if (!selected.isNullOrBlank()) {
                    val moveIdx = selected.toInt()
                    val clone = init.getClone()
                    val r = clone.applyMove(moves[moveIdx])
                    if (r) {
                        println("After Applying move:$moveIdx")
                        clone.printBoard()
                        println("Before Applying move")
                        init.printBoard()
                        init = clone
                        turn++
                    } else {
                        println("Invalid Move")
                    }
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