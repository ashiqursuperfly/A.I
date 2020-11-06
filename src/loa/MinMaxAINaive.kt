package loa

import java.lang.Integer.max
import java.lang.Integer.min

class MinMaxAINaive(
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

    private fun isGameOver(state: State): Boolean {
        return false
    }

    private fun getMinMax(
        state: State,
        depth: Int,
        alpha: Int,
        beta: Int,
        currentPlayerType: BoardPosition.ItemType,
        latestMove: Move?
    ): Triple<State, Move?, Int> {

        //println(":::${playerType}::")

        if (depth == 0 || isGameOver(state)) {
            return Triple(state, latestMove, getStaticEvaluation(state.white, state.black))
        }

        val player = if (currentPlayerType == BoardPosition.ItemType.W) state.white else state.black
        val opponent = if (currentPlayerType == BoardPosition.ItemType.W) state.black else state.white

        val moves = ArrayList<Move>()
        for (item in player.checkers) {
            moves.addAll(item.getValidMoves())
        }

        if (player.type == Constants.MAXIMIZING_PLAYER) {
            var newAlpha = alpha
            var maxEvaluation = Int.MIN_VALUE
            var maxState = state
            var maxMove: Move? = null

            for (move in moves) {
                val clone = state.getClone()
                clone.applyMove(move)

                val result = getMinMax(clone, depth - 1, newAlpha, beta, opponent.type, move)
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
        else {
            var newBeta = beta
            var minEvaluation = Int.MAX_VALUE
            var minState = state
            var minMove: Move? = null

            for (move in moves) {
                val clone = state.getClone()
                clone.applyMove(move)

                val result = getMinMax(clone, depth - 1, alpha, newBeta, opponent.type, move)
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

    }

    fun getMinMaxAIMove(): State {
        val res = getMinMax(
            board,
            maxDepth,
            Int.MIN_VALUE,
            Int.MAX_VALUE,
            playerType,
            null
        )
        println("A.I move: ${res.second}")

        return res.first
    }

}