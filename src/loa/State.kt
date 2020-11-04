package loa

import java.lang.StringBuilder

data class State(
    val white: Player = Player(BoardPosition.ItemType.W),
    val black: Player = Player(BoardPosition.ItemType.B),
    val loaFactory: LOAFactory = LOAFactory(),
    val board: Array<Array<BoardPosition>> = Array(Constants.BOARD_SIZE) {
        i -> Array(Constants.BOARD_SIZE) {
            j -> BoardPosition(row = 0, col = 0)
        }
    }
)
{
    init {
        for (i in 0 until Constants.BOARD_SIZE) {
            for (j in 0 until Constants.BOARD_SIZE) {
                board[i][j].row = i
                board[i][j].col = j
            }
        }
        initLOAs()
        initWhiteCheckers()
        initBlackCheckers()
    }

    private fun initWhiteCheckers() {
        for (i in 1 until Constants.BOARD_SIZE-1) {
            val bp = board[i][0]
            bp.item = BoardPosition.ItemType.W
            black.checkers.add(bp)
            bp.incrementAllLOAs()
        }
        for (i in 1 until Constants.BOARD_SIZE-1) {
            val bp = board[i][Constants.BOARD_SIZE-1]
            bp.item = BoardPosition.ItemType.W
            black.checkers.add(bp)
            bp.incrementAllLOAs()
        }
    }

    private fun initBlackCheckers() {
        for (i in 1 until Constants.BOARD_SIZE-1) {
            val bp = board[0][i]
            bp.item = BoardPosition.ItemType.B
            white.checkers.add(bp)
            bp.incrementAllLOAs()
        }
        for (i in 1 until Constants.BOARD_SIZE-1) {
            val bp = board[Constants.BOARD_SIZE-1][i]
            bp.item = BoardPosition.ItemType.B
            white.checkers.add(bp)
            bp.incrementAllLOAs()
        }
    }

    private fun initLOAs() {
        for (i in 0 until Constants.BOARD_SIZE) {
            for (j in 0 until Constants.BOARD_SIZE) {
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
        println("TOTAL LOA: ${loaFactory.all.size}")
    }

    fun applyMove(move: Move) : Boolean {
        val player = if (move.playerType == BoardPosition.ItemType.W) white else black
        val opponent = if (move.playerType == BoardPosition.ItemType.W) black else white

        val startPos = board[move.startCoord.first][move.startCoord.second]
        val endPos = board[move.endCoord.first][move.endCoord.second]

        val selectedLOA = startPos.getLOA(endPos) ?: return false
        val startOtherLOAs = startPos.getOtherLOAs(selectedLOA)
        val endOtherLOAs = endPos.getOtherLOAs(selectedLOA)

        // TODO:
        //  add capturing logic
        //  add overtaking logic

        for (item in startOtherLOAs)
            item.checkerCount--

        if (endPos.item == opponent.type) {
            opponent.checkers.remove(endPos)
            selectedLOA.checkerCount--
        }
        else {
            for (item in endOtherLOAs)
                item.checkerCount++
        }

        startPos.item = BoardPosition.ItemType.E
        endPos.item = player.type

        player.checkers.remove(startPos)
        player.checkers.add(endPos)

        return true
    }


    fun printBoard() {

        val sb = StringBuilder()

        sb.append(' ').append(' ').append(' ')
        for (i in 0 until Constants.BOARD_SIZE) {
            sb.append(i).append(' ').append(' ').append(' ')
        }
        sb.append('\n')

        for (i in 0 until Constants.BOARD_SIZE) {
            sb.append(i).append(' ').append(' ')
            for (j in 0 until Constants.BOARD_SIZE) {
                sb.append(board[i][j].item.value).append(' ').append('-').append(' ')
            }
            sb.append('\n')
        }

        println(sb.toString())
    }

}