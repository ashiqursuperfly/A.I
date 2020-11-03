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

	fun getLOA(endPos: BoardPosition) : LOA? {
		return when {
			horizontal.isBoardPositionInLOA(endPos) -> horizontal
			vertical.isBoardPositionInLOA(endPos) -> vertical
			topLeftToBottomRight.isBoardPositionInLOA(endPos) -> topLeftToBottomRight
			bottomLeftToTopRight.isBoardPositionInLOA(endPos) -> bottomLeftToTopRight
			else -> null
		}
	}

	fun getOtherLOAs(exclude: LOA): ArrayList<LOA> {

		val list = ArrayList<LOA>()
		if (exclude != horizontal) {
			list.add(horizontal)
		}
		if (exclude != vertical) {
			list.add(vertical)
		}
		if (exclude != topLeftToBottomRight) {
			list.add(topLeftToBottomRight)
		}
		if (exclude != bottomLeftToTopRight) {
			list.add(bottomLeftToTopRight)
		}

		return list
	}

    fun incrementAllLOAs() {
        horizontal.checkerCount++
		vertical.checkerCount++
		topLeftToBottomRight.checkerCount++
		bottomLeftToTopRight.checkerCount++
    }

	enum class ItemType(var value: String) {
		W("W"), B("B"), E("*");
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is BoardPosition) return false

		if (row != other.row) return false
		if (col != other.col) return false

		return true
	}

	override fun hashCode(): Int {
		var result = row
		result = 31 * result + col
		return result
	}

}