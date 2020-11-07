package loa.minmax

import loa.*
import kotlin.math.abs

object EvaluationFunctions {

    fun getStaticEvaluation(state: State): Int {
        return getAreaEvaluation(state) + 2 * getConnectedness(state) + 3 * getMobilityEvaluation(state) + 4 * getPieceSquareTableEvaluation(state)
    }

    private fun getAreaEvaluation(state: State): Int {

        val maximisingPlayer = if (Constants.MAXIMIZING_PLAYER == BoardPosition.ItemType.W) state.white else state.black
        val minimisingPlayer = if (Constants.MINIMIZING_PLAYER == BoardPosition.ItemType.W) state.white else state.black

        val maxArea = getAreaSinglePlayer(maximisingPlayer)
        val minArea = getAreaSinglePlayer(minimisingPlayer)

        return (minArea - maxArea)
    }

    private fun getAreaSinglePlayer(player: Player): Int {

        var (minRow, maxRow, minCol, maxCol) = listOf(Int.MAX_VALUE, Int.MIN_VALUE, Int.MAX_VALUE, Int.MIN_VALUE)

        for (block in player.checkers) {
            if (block.row > maxRow) maxRow = block.row
            if (block.row < minRow) minRow = block.row
            if (block.col > maxCol) maxCol = block.col
            if (block.col < minCol) minCol = block.col
        }
        val a = Pair(minRow, minCol)
        val b = Pair(maxRow, minCol)
        val c = Pair(maxRow, maxCol)

        val v1 = Pair(c.first-b.first, c.second-b.second)
        val v2 = Pair(a.first-b.first, a.second-b.second)

        return abs(v1.first * v2.second - v2.first * v1.second)

    }

    fun getMobilityEvaluation(state: State): Int {

        var maxMoves = 0
        var minMoves = 0

        val maximisingPlayer = if (Constants.MAXIMIZING_PLAYER == BoardPosition.ItemType.W) state.white else state.black
        val minimisingPlayer = if (Constants.MINIMIZING_PLAYER == BoardPosition.ItemType.W) state.white else state.black

        for (block in minimisingPlayer.checkers) {
            minMoves += block.getValidMoves().size
        }
        for (block in maximisingPlayer.checkers) {
            maxMoves += block.getValidMoves().size
        }

        return maxMoves - minMoves
    }

    fun getPieceSquareTableEvaluation(state: State): Int {

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

        val maximisingPlayer = if (Constants.MAXIMIZING_PLAYER == BoardPosition.ItemType.W) state.white else state.black
        val minimisingPlayer = if (Constants.MINIMIZING_PLAYER == BoardPosition.ItemType.W) state.white else state.black

        for (block in minimisingPlayer.checkers) {
            minSum += pieceSquareTable[block.row][block.col]
        }
        for (block in maximisingPlayer.checkers) {
            maxSum += pieceSquareTable[block.row][block.col]
        }

        return maxSum - minSum
    }

    fun getConnectedness(state: State): Int {
        val maximisingPlayer = if (Constants.MAXIMIZING_PLAYER == BoardPosition.ItemType.W) state.white else state.black
        val minimisingPlayer = if (Constants.MINIMIZING_PLAYER == BoardPosition.ItemType.W) state.white else state.black

        val max = getConnectednessSinglePlayer(maximisingPlayer)
        val min = getConnectednessSinglePlayer(minimisingPlayer)

        // println("ConnectedNess: ${Constants.MAXIMIZING_PLAYER} $max")
        // println("ConnectedNess: ${Constants.MINIMIZING_PLAYER} $min")

        return max-min
    }

    fun getConnectednessSinglePlayer(player: Player): Int {

        if (player.checkers.size <= 1) return Int.MAX_VALUE

        var maxConnectedComponentLength = Int.MIN_VALUE

        val visited = HashSet<Pair<Int, Int>>()
        val neighbouringPositions = HashSet<Pair<Int, Int>>()

        for (it1 in player.checkers) {

            val p = Pair(it1.row, it1.col)

            if (visited.contains(p)) continue

            neighbouringPositions.addAll(BoardPosition.findAdjacentPositions(p.first, p.second))
            visited.add(p)

            var totalConnectedNodes = 1 // 0 is by default in the forest

            while(totalConnectedNodes < player.checkers.size) {

                val inForest = player.checkers.filter {
                    val it2 = Pair(it.row, it.col)
                    neighbouringPositions.contains(it2)
                }

                totalConnectedNodes += inForest.size

                if (inForest.isNullOrEmpty() && totalConnectedNodes < player.checkers.size) {
                    // println("Length:${totalConnectedNodes}")
                    if (maxConnectedComponentLength < totalConnectedNodes) maxConnectedComponentLength = totalConnectedNodes
                    break
                }

                for (item in inForest) {
                    val it = Pair(item.row, item.col)
                    neighbouringPositions.remove(it)
                    visited.add(it)
                    val uniqueAdjacent = BoardPosition.findAdjacentPositions(item.row, item.col).filter {
                        !visited.contains(it)
                    }
                    neighbouringPositions.addAll(uniqueAdjacent)
                }


            }


        }



        return maxConnectedComponentLength
    }

}