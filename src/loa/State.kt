package loa

import java.lang.StringBuilder
import java.util.*

data class State(
    val white: Player = Player(BoardPosition.ItemType.W),
    val black: Player = Player(BoardPosition.ItemType.B),
    val loaFactory: LOAFactory = LOAFactory(),
    var board: Array<Array<BoardPosition>?> = Array(Constants.BOARD_SIZE) { i ->
        Array(Constants.BOARD_SIZE) { j ->
            BoardPosition(row = i, col = j)
        }
    }
)
{
    fun initDefaultBoard() {
        for (i in 0 until Constants.BOARD_SIZE) {
            for (j in 0 until Constants.BOARD_SIZE) {
                board[i]!![j].row = i
                board[i]!![j].col = j
            }
        }
        initLOAs()
        initWhiteCheckers()
        initBlackCheckers()
    }

    fun getClone(): State {
        // val copiedBoard = this.board.copyOf(this.board.size)
        val copiedBoard: Array<Array<BoardPosition>?> = Array(Constants.BOARD_SIZE) { i->
            Array(Constants.BOARD_SIZE) { j->
                BoardPosition(row = board[i]!![j].row, col = board[i]!![j].col, item = board[i]!![j].item)
            }
        }

        val whiteCheckers = ArrayList<BoardPosition>()
        for (i in 0 until Constants.BOARD_SIZE) {
            copiedBoard[i]?.filter {
                it.item == BoardPosition.ItemType.W
            }?.let {
                whiteCheckers.addAll(it)
            }
        }

        val blackCheckers = ArrayList<BoardPosition>()
        for (i in 0 until Constants.BOARD_SIZE) {
            copiedBoard[i]?.filter {
                it.item == BoardPosition.ItemType.B
            }?.let {
                blackCheckers.addAll(it)
            }
        }

        val white = Player(BoardPosition.ItemType.W, whiteCheckers)
        val black = Player(BoardPosition.ItemType.B, blackCheckers)

        val clone = State(
            white = white,
            black = black,
            loaFactory = LOAFactory(),
            board = copiedBoard
        )
        clone.initLOAs()
        for (item in loaFactory.all) {
            clone.loaFactory.all[item.key]?.checkerCount = item.value.checkerCount
        }

        return clone
    }

    private fun initWhiteCheckers() {
        for (i in 1 until Constants.BOARD_SIZE-1) {
            val bp = board[i]!![0]
            bp.item = BoardPosition.ItemType.W
            white.checkers.add(bp)
            bp.incrementAllLOACheckerCounts()
        }
        for (i in 1 until Constants.BOARD_SIZE-1) {
            val bp = board[i]!![Constants.BOARD_SIZE-1]
            bp.item = BoardPosition.ItemType.W
            white.checkers.add(bp)
            bp.incrementAllLOACheckerCounts()
        }
    }

    private fun initBlackCheckers() {
        for (i in 1 until Constants.BOARD_SIZE-1) {
            val bp = board[0]!![i]
            bp.item = BoardPosition.ItemType.B
            black.checkers.add(bp)
            bp.incrementAllLOACheckerCounts()
        }
        for (i in 1 until Constants.BOARD_SIZE-1) {
            val bp = board[Constants.BOARD_SIZE-1]!![i]
            bp.item = BoardPosition.ItemType.B
            black.checkers.add(bp)
            bp.incrementAllLOACheckerCounts()
        }
    }

    private fun initLOAs() {
        for (i in 0 until Constants.BOARD_SIZE) {
            for (j in 0 until Constants.BOARD_SIZE) {
                board[i]!![j].horizontal = loaFactory.getOrCreate(
                    LOAFactory.LOAType.H,
                    board[i]!![j],
                    this
                )
                board[i]!![j].vertical = loaFactory.getOrCreate(
                    LOAFactory.LOAType.V,
                    board[i]!![j],
                    this
                )
                board[i]!![j].topLeftToBottomRight = loaFactory.getOrCreate(
                    LOAFactory.LOAType.TL_BR,
                    board[i]!![j],
                    this
                )
                board[i]!![j].bottomLeftToTopRight = loaFactory.getOrCreate(
                    LOAFactory.LOAType.BL_TR,
                    board[i]!![j],
                    this
                )
            }
        }
    }

    fun applyMove(validMove: Move) : Boolean {
        val player = if (validMove.playerType == BoardPosition.ItemType.W) white else black
        val opponent = if (validMove.playerType == BoardPosition.ItemType.W) black else white

        val startPos = board[validMove.startCoord.first]!![validMove.startCoord.second]
        val endPos = board[validMove.endCoord.first]!![validMove.endCoord.second]

        val selectedLOA = startPos.getLOA(endPos) ?: return false
        val startOtherLOAs = startPos.getOtherLOAs(selectedLOA)
        val endOtherLOAs = endPos.getOtherLOAs(selectedLOA)

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
                sb.append(board[i]!![j].item.value).append(' ').append(Constants.BOARD_CHAR_SEPARATOR).append(' ')
            }
            sb.append('\n')
        }

        println(sb.toString())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is State) return false

        if (!board.contentDeepEquals(other.board)) return false

        return true
    }

    override fun hashCode(): Int {
        return board.contentDeepHashCode()
    }


}