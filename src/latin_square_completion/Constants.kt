package latin_square_completion

object Constants {
    const val EMPTY = 0
    enum class LatinSquareHeuristic {
        SMALLEST_DOMAIN_FIRST, BRELAZ_HIGHEST_COLOR_SATURATION, MAX_DYNAMIC_DEGREE
    }
}