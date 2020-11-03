package loa

data class Player (
    val type: BoardPosition.ItemType,
    val checkers: ArrayList<BoardPosition> = ArrayList()
)