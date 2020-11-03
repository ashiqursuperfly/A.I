package loa

import java.lang.StringBuilder

data class State(
    val white: Player = Player(BoardPosition.ItemType.W),
    val black: Player = Player(BoardPosition.ItemType.B),
    val loaFactory: LOAFactory = LOAFactory(),
    val board: Array<Array<BoardPosition>> = Array(8) {
        i -> Array(8) {
            j -> BoardPosition(row = 0, col = 0)
        }
    }
)
{
    init {
        for (i in 0 until 8) {
            for (j in 0 until 8) {
                board[i][j].row = i
                board[i][j].col = j
            }
        }
        initLOAs()
        initWhiteCheckers()
        initBlackCheckers()
    }

    private fun initWhiteCheckers() {
        for (i in 1 until 7) {
            val bp = board[i][0]
            bp.item = BoardPosition.ItemType.W
            black.checkers.add(bp)
            bp.incrementAllLOAs()
        }
        for (i in 1 until 7) {
            val bp = board[i][7]
            bp.item = BoardPosition.ItemType.W
            black.checkers.add(bp)
            bp.incrementAllLOAs()
        }
    }

    private fun initBlackCheckers() {
        for (i in 1 until 7) {
            val bp = board[0][i]
            bp.item = BoardPosition.ItemType.B
            white.checkers.add(bp)
            bp.incrementAllLOAs()
        }
        for (i in 1 until 7) {
            val bp = board[7][i]
            bp.item = BoardPosition.ItemType.B
            white.checkers.add(bp)
            bp.incrementAllLOAs()
        }
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
        println("TOTAL LOA: ${loaFactory.all.size}")
    }

    fun applyMove(move: Move) : Boolean {
        val player = if (move.playerType == BoardPosition.ItemType.W) white else black
        val startPos = board[move.startCoord.first][move.startCoord.second]
        val endPos = board[move.endCoord.first][move.endCoord.second]

        val selectedLOA = startPos.getLOA(endPos) ?: return false
        val startOtherLOAs = startPos.getOtherLOAs(selectedLOA)
        val endOtherLOAs = endPos.getOtherLOAs(selectedLOA)

        for (item in startOtherLOAs)
            item.checkerCount--

        for (item in endOtherLOAs)
            item.checkerCount++

        startPos.item = BoardPosition.ItemType.E
        endPos.item = player.type

        player.checkers.remove(startPos)
        player.checkers.add(endPos)

        // TODO:

        return true
    }


    fun printBoard() {

        val sb = StringBuilder()

        sb.append(' ').append(' ').append(' ')
        for (i in 0 until 8) {
            sb.append(i).append(' ').append(' ').append(' ')
        }
        sb.append('\n')

        for (i in 0 until 8) {
            sb.append(i).append(' ').append(' ')
            for (j in 0 until 8) {
                sb.append(board[i][j].item.value).append(' ').append('-').append(' ')
            }
            sb.append('\n')
        }

        println(sb.toString())
    }

}