package latin_square_completion

import latin_square_completion.permutation.Permutation
import java.lang.StringBuilder

val EMPTY = 0

val data = arrayListOf(
    arrayListOf(1, 0, 0, 4),
    arrayListOf(0, 0, 2, 0),
    arrayListOf(3, 0, 1, 0),
    arrayListOf(0, 3, 0, 0)
)
val colHashSets = ArrayList<HashSet<Int>>()
fun initColHashSet() {
    for (i in data.indices)
        colHashSets.add(HashSet<Int>())

    for (row in data) {
        for ((index, item) in row.withIndex()) {
            if (item != EMPTY) {
               colHashSets[index].add(item)
            }
        }
    }
}

fun main () {

    initColHashSet()
    solve(data)

}

fun getUnFinishedRow(data: ArrayList<ArrayList<Int>>): Int {
    for ((index, row) in data.withIndex()) {
        for (col in row) {
            if (col == EMPTY) {
                return index
            }
        }
    }
    return -1
}

fun getRemainingClues(ints: ArrayList<Int>): String {

    val all = ArrayList(IntArray(ints.size) { i -> i + 1 }.asList())

    /*println(all)
    println(ints)*/

    for (item in ints) {
        if(item != EMPTY) {
            all.remove(item)
        }
    }
    val sb = StringBuilder()
    for (item in all)
        sb.append(item)

    return sb.toString()
}

fun solve(data: ArrayList<ArrayList<Int>>) {
    val nextUnfinishedRow = getUnFinishedRow(data)

    if (nextUnfinishedRow == -1) {
        println("Solution:\n$data")
        return
    }

    val emptyIdxs = getRemainingClues(data[nextUnfinishedRow])
    //println(emptyIdxs)
    val permutation = Permutation(emptyIdxs)

    for (item in permutation.calculatePermute()) {
        val copiedData = clone(data)
        // println(copiedData)
        // println(item)
        if (completeRow(nextUnfinishedRow, copiedData, item)) {
            //println(copiedData)
            solve(copiedData)
        }
    }



}

fun clone(data: ArrayList<ArrayList<Int>>): ArrayList<ArrayList<Int>> {
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
    return copied
}

fun completeRow(nextUnfinishedRow: Int, copiedData: ArrayList<ArrayList<Int>>, values: String): Boolean {
    var j = 0

    for (i in copiedData.indices) {
        if (copiedData[nextUnfinishedRow][i] == EMPTY) {
            val value = Integer.parseInt(values[j].toString())
            if (colHashSets[i].contains(value)) return false

            copiedData[nextUnfinishedRow][i] = value
            j++
        }
    }

    return true
}


