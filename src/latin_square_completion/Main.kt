package latin_square_completion

import latin_square_completion.permutation.LatinSquareSolver

val data = arrayListOf(
    arrayListOf(0, 0, 0, 0),
    arrayListOf(0, 0, 0, 0),
    arrayListOf(0, 0, 0, 0),
    arrayListOf(0, 0, 0, 0)
)


fun main() {
    val latinSquare = LatinSquare(data = data)
    latinSquare.initColHashSet()

    LatinSquareSolver.solve(latinSquare)
}






