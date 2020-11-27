package latin_square_completion

import latin_square_completion.Constants.EMPTY
import latin_square_completion.heuristics.SmallestDomainFirst
import utils.permutation.Permutation
import java.util.*
import kotlin.collections.ArrayList
import kotlin.test.assertEquals

object LatinSquareSolver {

    val heuristic: Constants.LatinSquareHeuristic = Constants.LatinSquareHeuristic.SMALLEST_DOMAIN_FIRST
    var consistencyCheckingCount = 0
    var solutionCount = 0
    var failCount = 0

    private fun getUnFinishedRow(latin: LatinSquare): Int {

        for ((index, row) in latin.data.withIndex()) {
            for (col in row) {
                if (col == EMPTY) {
                    return index
                }
            }
        }

        return -1
    }

    private fun getUnFinishedRowHeuristic(latin: LatinSquare): Int {

        val comparator = when (heuristic) {
            Constants.LatinSquareHeuristic.SMALLEST_DOMAIN_FIRST -> {
                SmallestDomainFirst()
            }
            Constants.LatinSquareHeuristic.BRELAZ_HIGHEST_COLOR_SATURATION -> TODO()
            Constants.LatinSquareHeuristic.MAX_DYNAMIC_DEGREE -> TODO()
        }

        val priorityQueue: PriorityQueue<LatinRowCompareData> = PriorityQueue(comparator)

        for ((index, row) in latin.data.withIndex()) {
            for (col in row) {
                if (col == EMPTY) {
                    priorityQueue.add(
                        LatinRowCompareData(
                            rowIdx = index,
                            latinSquare = latin
                        )
                    )
                    break
                }
            }
        }

        return if (priorityQueue.isEmpty()) -1
        else priorityQueue.peek().rowIdx
    }

    private fun getRemainingClues(ints: ArrayList<Int>): ArrayList<Int> {

        val all = ArrayList(IntArray(ints.size) { i -> i + 1 }.asList())

        for (item in ints) {
            if (item != EMPTY) {
                all.remove(item)
            }
        }
        val result = ArrayList<Int>()
        for (item in all)
            result.add(item)

        return result
    }

    private fun completeRow(nextUnfinishedRow: Int, latin: LatinSquare, values: ArrayList<Int>): Boolean {

        consistencyCheckingCount++

        var j = 0

        for (i in latin.data.indices) {
            if (latin.data[nextUnfinishedRow][i] == EMPTY) {
                val value = values[j]
                if (latin.colHashSets[i].contains(value)) return false

                assertEquals(false, latin.rowHashSets[nextUnfinishedRow].contains(value))

                latin.data[nextUnfinishedRow][i] = value
                latin.colHashSets[i].add(value)
                latin.rowHashSets[nextUnfinishedRow].add(value)
                j++
            }
        }

        return true
    }

    fun solve(latin: LatinSquare) {

        if (solutionCount > 0) return

        val nextUnfinishedRow = getUnFinishedRowHeuristic(latin)  // getUnFinishedRow(latin)

        if (nextUnfinishedRow == -1) {
            println("Solution:\n${latin}")
            solutionCount++
            return
        }

        val remainingClues = getRemainingClues(latin.data[nextUnfinishedRow])
        val permutation = Permutation(remainingClues)
        permutation.calculatePermute()

        for (item in permutation.results) {
            val copiedData = latin.clone()
            if (completeRow(nextUnfinishedRow, copiedData, item)) {
                solve(copiedData)
            } else failCount++
        }
    }

}