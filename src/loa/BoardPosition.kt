package loa

import kotlin.test.assertEquals

data class BoardPosition(
	var row: Int,
	var col: Int,
	var item: ItemType = ItemType.E,
	var horizontal: LOA = LOA(),
	var vertical: LOA = LOA(),
	var topLeftToBottomRight: LOA = LOA(),
	var bottomLeftToTopRight: LOA = LOA()
) {
	enum class ItemType(var value: String) {
		B("⚫"), W("⚪"), E("➖");
	}
	companion object {
		fun findAdjacentPositions(row: Int, col: Int): ArrayList<Pair<Int, Int>> {

			val list = ArrayList<Pair<Int, Int>>()

			val top = row - 1
			val left = col - 1
			val down = row + 1
			val right = col + 1

			if (top >= 0) {
				list.add(Pair(top, col))
				if (left >= 0) {
					list.add(Pair(top, left))
				}
				if (right < Constants.BOARD_SIZE) {
					list.add(Pair(top, right))
				}
			}
			if (down < Constants.BOARD_SIZE) {
				list.add(Pair(down, col))
				if (left >= 0) {
					list.add(Pair(down, left))
				}
				if (right < Constants.BOARD_SIZE) {
					list.add(Pair(down, right))
				}
			}
			if (left >= 0) {
				list.add(Pair(row, left))
			}
			if (right < Constants.BOARD_SIZE) {
				list.add(Pair(row, right))
			}

			return list

		}
	}

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

    fun incrementAllLOACheckerCounts() {
        horizontal.checkerCount++
		vertical.checkerCount++
		topLeftToBottomRight.checkerCount++
		bottomLeftToTopRight.checkerCount++
    }

	fun getValidMoves(): ArrayList<Move> {
		val moves = ArrayList<Move>()
		val h = getHorizontalValidMoves()
		val v = getVerticalValidMoves()
		val tlbr = getDiagonalLOAMoves(topLeftToBottomRight)
		val bltr = getDiagonalLOAMoves(bottomLeftToTopRight)

		/*
		println("h: $h")
		println("v: $v")
		println("tlbr: $tlbr")
		println("bltr: $bltr")
		*/
		moves.addAll(h)
		moves.addAll(v)
		moves.addAll(bltr)
		moves.addAll(tlbr)


		return moves
	}

	private fun getHorizontalValidMoves(): ArrayList<Move> {

		val moves = ArrayList<Move>()

		val loaHead = horizontal.path[0]
		val loaTail = horizontal.path[horizontal.path.size-1]

		assertEquals(loaHead.row, loaTail.row)

		val toRow = loaHead.row
		val toColPositiveDir = this.col + horizontal.checkerCount
		val toColDirNegativeDir = this.col - horizontal.checkerCount

		if (toColPositiveDir < Constants.BOARD_SIZE) {
			val m = Move(
				this.item,
				Pair(row, col),
				Pair(toRow, toColPositiveDir)
			)
			if (checkValidOverlap(1, m, horizontal)) {
				moves.add(m)
			}
		}
		if (toColDirNegativeDir >= 0) {
			val m = Move(
				this.item,
				Pair(row, col),
				Pair(toRow, toColDirNegativeDir)
			)
			if (checkValidOverlap(-1, m, horizontal)) {
				moves.add(m)
			}
		}

		return moves

	}

	private fun getVerticalValidMoves(): ArrayList<Move> {

		val moves = ArrayList<Move>()

		val loaHead = vertical.path[0]
		val loaTail = vertical.path[vertical.path.size-1]

		assertEquals(loaHead.col, loaTail.col)

		val toCol = loaHead.col
		val toRowPositiveDir = this.row + vertical.checkerCount
		val toRowNegativeDir = this.row - vertical.checkerCount

		if (toRowPositiveDir < Constants.BOARD_SIZE) {
			val m = Move(
				this.item,
				Pair(row, col),
				Pair(toRowPositiveDir, toCol)
			)
			if (checkValidOverlap(1, m, vertical)) {
				moves.add(m)
			}
		}
		if (toRowNegativeDir >= 0) {
			val m = Move(
				this.item,
				Pair(row, col),
				Pair(toRowNegativeDir, toCol)
			)
			if (checkValidOverlap(-1, m, vertical)) {
				moves.add(m)
			}
		}

		return moves

	}

	private fun getDiagonalLOAMoves(loa: LOA): ArrayList<Move> {

		val moves = ArrayList<Move>()

		val startIdx = loa.path.indexOf(this)

		val endPositiveDirIdx = startIdx + loa.checkerCount
		if (endPositiveDirIdx < loa.path.size) {
			val endPositiveDir = loa.path[endPositiveDirIdx]
			val m = Move(
				this.item,
				Pair(row, col),
				Pair(endPositiveDir.row, endPositiveDir.col)
			)
			if (checkValidOverlap(-1, m, loa)) {
				moves.add(m)
			}
		}

		val endNegativeDirIdx = startIdx - loa.checkerCount
		if (endNegativeDirIdx >= 0) {
			val endNegativeDir = loa.path[endNegativeDirIdx]
			val m = Move(
				this.item,
				Pair(row, col),
				Pair(endNegativeDir.row, endNegativeDir.col)
			)
			if (checkValidOverlap(-1, m, loa)) {
				moves.add(m)
			}
		}

		return moves
	}


	/**
	* @param dir value is either +1 or -1
    */
	private fun checkValidOverlap(dir: Int, move: Move, loa: LOA) : Boolean {

		if (dir != -1 && dir != 1) throw IllegalArgumentException("Dir Value must either be +1 or -1")

		val start = loa.path.find {
			it.row == move.startCoord.first && it.col == move.startCoord.second
		}
		val end = loa.path.find {
			it.row == move.endCoord.first && it.col == move.endCoord.second
		}

		if (move.playerType == end?.item) return false

		val startIdx = loa.path.indexOf(start)
		if (dir == 1) {
			for (i in startIdx until loa.path.size) {
				if (loa.path[i] == end) {
					return true
				}

				val item = loa.path[i].item

				if (item != ItemType.E && item != move.playerType) {
					return false
				}
			}
		}
		else {
			for (i in startIdx until 0) {
				if (loa.path[i] == end) {
					return true
				}

				val item = loa.path[i].item

				if (item != ItemType.E && item != move.playerType) {
					return false
				}
			}
		}

		return true
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