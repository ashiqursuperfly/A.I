package loa

data class State(
    val loaFactory: LOAFactory = LOAFactory(),
    val board: Array<Array<BoardPosition>> = Array(8) {
        i -> Array(8) {
            j -> BoardPosition(0, 0)
        }
    }
)
{
    init {
        for (i in 0 until 8) {
            for (j in 0 until 8) {
                val b = BoardPosition(
                        row = i,
                        col = j
                )
                board[i][j] = b
            }
        }
        initLOAs()
    }

    private fun initLOAs() {
        for (i in 0 until 8) {
            for (j in 0 until 8) {
                board[i][j].horizontal = loaFactory.getOrCreate(
                    LOAFactory.LOAType.H,
                    board[i][j],
                    this
                )
                board[i][j].vertical = loaFactory.getOrCreate(
                    LOAFactory.LOAType.V,
                    board[i][j],
                    this
                )
                board[i][j].topLeftToBottomRight = loaFactory.getOrCreate(
                    LOAFactory.LOAType.TL_BR,
                    board[i][j],
                    this
                )
                board[i][j].bottomLeftToTopRight = loaFactory.getOrCreate(
                    LOAFactory.LOAType.BL_TR,
                    board[i][j],
                    this
                )
            }
        }
    }

}