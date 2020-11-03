package loa

data class BoardPosition(
	var row: Int,
	var col: Int,
	val item: ItemType = ItemType.EMPTY,
	var horizontal: LOA? = null,
	var vertical: LOA? = null,
	var topLeftToBottomRight: LOA? = null,
	var bottomLeftToTopRight: LOA? = null
) {
    enum class ItemType {
        WHITE, BLACK, EMPTY
    }
}