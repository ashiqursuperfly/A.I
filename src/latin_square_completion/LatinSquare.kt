package latin_square_completion

import java.lang.StringBuilder

data class LatinSquare (
    var data: ArrayList<ArrayList<Int>>,
    var colHashSets : ArrayList<HashSet<Int>> = ArrayList()
) {

    fun initColHashSet() {
        colHashSets = ArrayList()

        for (i in data.indices) {
            colHashSets.add(HashSet())
        }

        for (row in data) {
            for ((index, item) in row.withIndex()) {
                if (item != Constants.EMPTY) {
                    colHashSets[index].add(item)
                }
            }
        }
    }

    fun clone(): LatinSquare {
        val copied = ArrayList<ArrayList<Int>>()
        for ((i, row) in data.withIndex()) {
            copied.add(ArrayList())
            for ((j, col) in row.withIndex()) {
                copied[i].add(-1)
            }
        }
        for ((i, row) in data.withIndex()) {
            for ((j, col) in row.withIndex()) {
                copied[i][j] = col
            }
        }
        val copy = LatinSquare(data = copied)
        copy.initColHashSet()
        return copy
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (row in data) {
            for (item in row) {
                sb.append(item).append(' ')
            }
            sb.append('\n')
        }

        return sb.toString()
    }
}