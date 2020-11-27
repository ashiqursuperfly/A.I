package latin_square_completion

import java.lang.StringBuilder

data class LatinSquare (
    var data: ArrayList<ArrayList<Int>>,
    var colHashSets : ArrayList<HashSet<Int>> = ArrayList(),
    var rowHashSets : ArrayList<HashSet<Int>> = ArrayList()
) {

    init {
       initColHashSet()
       initRowHashSets()
    }

    private fun initRowHashSets() {
        rowHashSets = ArrayList()

        for (i in data.indices) {
            rowHashSets.add(HashSet())
        }

        for ((i, row) in data.withIndex()) {
            rowHashSets[i].addAll(row.filter { it != Constants.EMPTY })
        }
    }

    private fun initColHashSet() {
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
            copied.add(ArrayList(row.toMutableList()))
        }
        return LatinSquare(data = copied)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (row in data) {
            for (item in row) {
                if (item < 10) {
                    sb.append(' ').append(item)
                }
                else sb.append(item)
                sb.append(' ').append(' ')
            }
            sb.append('\n')
        }

        return sb.toString()
    }
}