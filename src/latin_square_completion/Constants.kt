package latin_square_completion

object Constants {
    const val EMPTY = 0
    enum class LatinSquareHeuristic {
        SMALLEST_DOMAIN_FIRST, BRELAZ, MAX_DYNAMIC_DEGREE
    }
}