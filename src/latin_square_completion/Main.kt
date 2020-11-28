package latin_square_completion

import utils.FileUtil

var fileLine = 0
lateinit var latinSquareInitData: ArrayList<ArrayList<Int>>
fun processLine(line: String) {
    if (fileLine == 0) {
        val n = Integer.parseInt(line.trim())
        latinSquareInitData = ArrayList()
        for (i in 0 until n) {
            latinSquareInitData.add(ArrayList())
            for (j in 0 until n) {
                latinSquareInitData[i].add(-1)
            }
        }
    } else {
        val items = line.split(",")
        for ((j, item) in items.withIndex()) {
            latinSquareInitData[fileLine - 1][j] = Integer.parseInt(item.trim())
        }
    }
    fileLine++
}

fun main() {

    FileUtil.readFileLineByLineUsingForEachLine(
        "in.txt",
        ::processLine
    )

    val latinSquare = LatinSquare(data = latinSquareInitData)
    println(latinSquare)

    val startTime = System.currentTimeMillis()
    LatinSquareSolver.solve(latinSquare, true)
    val endTime = System.currentTimeMillis()
    println("Count: ${LatinSquareSolver.failCount} ${LatinSquareSolver.consistencyCheckingCount} ${LatinSquareSolver.solutionCount}")
    println((endTime - startTime))

    /*
    2 -> 2
    3 -> 12
    4 -> 576
    5 -> 161280
    */
}






