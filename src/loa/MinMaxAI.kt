package loa

import java.lang.IllegalArgumentException
import java.lang.Integer.max
import java.lang.Integer.min

class MinMaxAI(
    private val maxDepth: Int,
    private var root: State,
    private val playerType: BoardPosition.ItemType
) {

    private fun getStaticEvaluation(white: Player, black: Player): Int {

        val pieceSquareTable = arrayOf(
            intArrayOf(-80, -25, -20, -20, -20, -20, -25, -80),
            intArrayOf(-25, 10, 10, 10, 10, 10, 10, -25),
            intArrayOf(-20, 10, 25, 25, 25, 25, 10, -20),
            intArrayOf(-20, 10, 25, 50, 50, 25, 10, -20),
            intArrayOf(-20, 10, 25, 50, 50, 25, 10, -20),
            intArrayOf(-20, 10, 25, 25, 25, 25, 10, -20),
            intArrayOf(-25, 10, 10, 10, 10, 10, 10, -25),
            intArrayOf(-80, -25, -20, -20, -20, -20, -25, -80)
        )

        var maxSum = 0
        var minSum = 0

        val maximisingPlayer = if (Constants.MAXIMIZING_PLAYER == BoardPosition.ItemType.W) white else black
        val minimisingPlayer = if (Constants.MINIMIZING_PLAYER == BoardPosition.ItemType.W) white else black

        for (block in minimisingPlayer.checkers) {
            minSum += pieceSquareTable[block.row][block.col]
        }
        for (block in maximisingPlayer.checkers) {
            maxSum += pieceSquareTable[block.row][block.col]
        }

        return maxSum - minSum
    }

    fun isGameOver(state: State): Boolean {
        if (isGameOver(state.black)) {
            return true
        }
        else return isGameOver(state.white)
    }

    private fun isGameOver(player: Player): Boolean {

        if (player.checkers.size <= 1) return true


        val forest = HashSet<Pair<Int, Int>>()

        val availablePositions = HashSet<Pair<Int, Int>>()
        availablePositions.addAll(findAdjacentPositions(player.checkers[0].row, player.checkers[0].col))
        forest.add(Pair(player.checkers[0].row, player.checkers[0].col))

        var totalConnectedNodes = 1 // 0 is by default in the forest

        while(totalConnectedNodes < player.checkers.size) {

            val inForest = player.checkers.filter {
                val it2 = Pair(it.row, it.col)
                availablePositions.contains(it2)
            }

            totalConnectedNodes += inForest.size

            if (inForest.isNullOrEmpty()) return false

            for (item in inForest) {
                val it = Pair(item.row, item.col)
                availablePositions.remove(it)
                forest.add(it)
                val uniqueAdjacent = findAdjacentPositions(item.row, item.col).filter {
                    !forest.contains(it)
                }
                availablePositions.addAll(uniqueAdjacent)
            }

        }

        return true
    }

    private fun findAdjacentPositions(row: Int, col: Int): ArrayList<Pair<Int, Int>> {

        val list = ArrayList<Pair<Int, Int>>()

        val top = row - 1
        val left = col - 1
        val down = row + 1
        val right = col + 1

        if (top >= 0) {
            list.add(Pair(top, col))
            if (left >= 0) {
                list.add(Pair(top, left))
            }
            if (right < Constants.BOARD_SIZE) {
                list.add(Pair(top, right))
            }
        }
        if (down < Constants.BOARD_SIZE) {
            list.add(Pair(down, col))
            if (left >= 0) {
                list.add(Pair(down, left))
            }
            if (right < Constants.BOARD_SIZE) {
                list.add(Pair(down, right))
            }
        }
        if (left >= 0) {
            list.add(Pair(row, left))
        }
        if (right < Constants.BOARD_SIZE) {
            list.add(Pair(row, right))
        }

        return list

    }

    fun getMinMaxAIMove(): State {

        val result: Triple<State, Move?, Int>

        if (playerType == Constants.MAXIMIZING_PLAYER) {
            result = maxValue(
                root,
                maxDepth,
                Int.MIN_VALUE,
                Int.MAX_VALUE,
                null
            )
        } else { // if (playerType == Constants.MINIMIZING_PLAYER)
            result = minValue(
                root,
                maxDepth,
                Int.MIN_VALUE,
                Int.MAX_VALUE,
                null
            )
        }
        if (result.second == null) throw IllegalArgumentException("Could not find A.I Move")

        println("A.I Move: ${result.second}")
        return result.first

    }

    private fun minValue(
        state: State,
        depth: Int,
        alpha: Int,
        beta: Int,
        latestMove: Move?
    ): Triple<State, Move?, Int> {

        if (depth == 0 || isGameOver(state)) {
            return Triple(state, latestMove, getStaticEvaluation(state.white, state.black))
        }

        val player = if (Constants.MINIMIZING_PLAYER == BoardPosition.ItemType.W) state.white else state.black

        var newBeta = beta
        var minEvaluation = Int.MAX_VALUE
        var minState = state
        var minMove: Move? = null

        val moves = ArrayList<Move>()
        for (item in player.checkers) {
            moves.addAll(item.getValidMoves())
        }
        for (move in moves) {
            val clone = state.getClone()
            clone.applyMove(move)

            val result = maxValue(clone, depth - 1, alpha, newBeta, move)
            val newEvaluation = result.third

            if (minEvaluation > newEvaluation) {
                minEvaluation = newEvaluation
                minState = clone
                minMove = move
            }
            newBeta = min(beta, newEvaluation)
            if (beta <= alpha) break


        }
        return Triple(minState, minMove, minEvaluation)

    }

    private fun maxValue(
        state: State,
        depth: Int,
        alpha: Int,
        beta: Int,
        latestMove: Move?
    ): Triple<State, Move?, Int> {

        if (depth == 0 || isGameOver(state)) {
            return Triple(state, latestMove, getStaticEvaluation(state.white, state.black))
        }

        val player = if (Constants.MAXIMIZING_PLAYER == BoardPosition.ItemType.W) state.white else state.black

        var newAlpha = alpha
        var maxEvaluation = Int.MIN_VALUE
        var maxState = state
        var maxMove: Move? = null

        val moves = ArrayList<Move>()
        for (item in player.checkers) {
            moves.addAll(item.getValidMoves())
        }
        for (move in moves) {
            val clone = state.getClone()
            clone.applyMove(move)

            val result = minValue(clone, depth - 1, newAlpha, beta, move)
            val newEvaluation = result.third

            if (maxEvaluation < newEvaluation) {
                maxEvaluation = newEvaluation
                maxState = clone
                maxMove = move
            }
            newAlpha = max(alpha, newEvaluation)
            if (beta <= alpha) break


        }

        return Triple(maxState, maxMove, maxEvaluation)

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val board = State()
            val minMax = MinMaxAI(
                maxDepth = 3,
                root = board,
                ai
            )
            board.initDefaultBoard()
            minMax.isGameOver(board)
            //minMax.findAdjacentPositions(0,1)
        }
    }

}

