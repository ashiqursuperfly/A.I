package loa

data class BoardPosition(
	var row: Int,
	var col: Int,
	var item: ItemType = ItemType.E,
	var horizontal: LOA = LOA(),
	var vertical: LOA = LOA(),
	var topLeftToBottomRight: LOA = LOA(),
	var bottomLeftToTopRight: LOA = LOA()
) {
    fun incrementAllLOAs() {
        horizontal.checkerCount++
		vertical.checkerCount++
		topLeftToBottomRight.checkerCount++
		bottomLeftToTopRight.checkerCount++
    }

    enum class ItemType(var value: String) {
		W("W"), B("B"), E("*");
	}
}