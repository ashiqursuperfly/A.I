package latin_square_completion

import latin_square_completion.Constants.EMPTY
import utils.permutation.Permutation

object LatinSquareSolver {

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
        var j = 0

        for (i in latin.data.indices) {
            if (latin.data[nextUnfinishedRow][i] == EMPTY) {
                val value = values[j]
                if (latin.colHashSets[i].contains(value)) return false
                latin.data[nextUnfinishedRow][i] = value
                latin.colHashSets[i].add(value)
                j++
            }
        }

        return true
    }

    fun solve(latin: LatinSquare) {
        val nextUnfinishedRow = getUnFinishedRow(latin.data)

        if (nextUnfinishedRow == -1) {
            println("Solution:\n${latin}")
            return
        }

        val emptyIDXs = getRemainingClues(latin.data[nextUnfinishedRow])
        val permutation = Permutation(emptyIDXs)
        permutation.calculatePermute()

        for (item in permutation.results) {
            val copiedData = latin.clone()
            // println(copiedData)
            // println(item)
            if (completeRow(nextUnfinishedRow, copiedData, item)) {
                // println(copiedData)
                solve(copiedData)
            }
        }
    }

}