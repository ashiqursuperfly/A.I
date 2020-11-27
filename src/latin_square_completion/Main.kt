package latin_square_completion

import utils.FileUtil

var i = 0
lateinit var data: ArrayList<ArrayList<Int>>
fun processLine(line: String) {
    if (i == 0) {
        val n = Integer.parseInt(line.trim())
        data = ArrayList()
        for (i in 0 until n) {
            data.add(ArrayList())
            for (j in 0 until n) {
                data[i].add(-1)
            }
        }
    } else {
        val items = line.split(",")
        for ((j, item) in items.withIndex()) {
            data[i-1][j] = Integer.parseInt(item.trim())
        }
    }
    i++
}

fun main() {

    FileUtil.readFileLineByLineUsingForEachLine(
        "in.txt",
        ::processLine
    )

    val latinSquare = LatinSquare(data = data)
    latinSquare.initColHashSet()
    println(latinSquare)
    val startTime = System.currentTimeMillis()
    LatinSquareSolver.solve(latinSquare)
    val endTime = System.currentTimeMillis()
    println("Count: ${LatinSquareSolver.failCount} ${LatinSquareSolver.consistencyCheckingCount} ${LatinSquareSolver.solutionCount}")
    println((endTime-startTime)/1000.0f)
    /*
    2 -> 2
    3 -> 12
    4 -> 576
    5 -> 161280
     */
}






