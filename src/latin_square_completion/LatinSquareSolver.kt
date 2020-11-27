package latin_square_completion

import latin_square_completion.Constants.EMPTY
import utils.permutation.Permutation

object LatinSquareSolver {

    var consistencyCheckingCount = 0
    var solutionCount = 0
    var failCount = 0

    private fun getUnFinishedRow(data: ArrayList<ArrayList<Int>>): Int {
        for ((index, row) in data.withIndex()) {
            for (col in row) {
                if (col == EMPTY) {
                    return index
                }
            }
        }
        return -1
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

        val nextUnfinishedRow = getUnFinishedRow(latin.data)

        if (nextUnfinishedRow == -1) {
            println("Solution:\n${latin}")
            solutionCount++
            return
        }

        val remainingClues = getRemainingClues(latin.data[nextUnfinishedRow])
        val permutation = Permutation(remainingClues)
        permutation.calculatePermute()
        // println("remaining clues: ${remainingClues.size}")
        for (item in permutation.results) {
            val copiedData = latin.clone()
            // println(copiedData)
            // println(item)
            if (completeRow(nextUnfinishedRow, copiedData, item)) {
                // println(nextUnfinishedRow)
                solve(copiedData)
            }
            else failCount++
        }
    }

}