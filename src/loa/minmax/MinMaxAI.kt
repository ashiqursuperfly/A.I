package loa.minmax

import loa.*
import java.lang.IllegalArgumentException
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.log2

class MinMaxAI(
    private val maxDepth: Int,
    private var root: State,
    private val playerType: BoardPosition.ItemType
) {

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
        availablePositions.addAll(BoardPosition.findAdjacentPositions(player.checkers[0].row, player.checkers[0].col))
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
                val uniqueAdjacent = BoardPosition.findAdjacentPositions(item.row, item.col).filter {
                    !forest.contains(it)
                }
                availablePositions.addAll(uniqueAdjacent)
            }

        }

        return true
    }

    fun getMinMaxAIMove(): State {

        val player = if (playerType == BoardPosition.ItemType.W) board.white else board.black

        val optimalDepth = calculateOptimalDepth(player)

        println("D:${optimalDepth}")

        val result: Triple<State, Move?, Int>

        if (playerType == Constants.MAXIMIZING_PLAYER) {
            result = maxValue(
                root,
                optimalDepth,
                Int.MIN_VALUE,
                Int.MAX_VALUE,
                null
            )
        } else { // if (playerType == Constants.MINIMIZING_PLAYER)
            result = minValue(
                root,
                optimalDepth,
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
            return Triple(state, latestMove, EvaluationFunctions.getStaticEvaluation(state))
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
            return Triple(state, latestMove, EvaluationFunctions.getStaticEvaluation(state))
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

    private fun calculateOptimalDepth(player: Player) : Int {
        val d = ceil(maxDepth - log2(player.checkers.size.toDouble())).toInt()

        if (d == 0) return 1
        // else if (d > maxDepth) maxDepth
        return d
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
            //minMax.isGameOver(board)
            //minMax.findAdjacentPositions(0,1)

        }
    }

}

