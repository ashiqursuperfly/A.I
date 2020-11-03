package loa

data class Move (
    val playerType: BoardPosition.ItemType,
    val startCoord: Pair<Int, Int>,
    val endCoord: Pair<Int, Int>
) {
    override fun toString(): String {
        return "Move($playerType, start=$startCoord, end=$endCoord)"
    }

}