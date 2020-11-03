package loa

data class BoardPosition (
    val row: Int,
    val col: Int,
    var horizontal: LOA? = null,
    var vertical: LOA? = null,
    var topLeftToBottomRight: LOA? = null,
    var bottomLeftToTopRight: LOA? = null
)