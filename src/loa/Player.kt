package loa

enum class PlayerType {
    W,B
}
data class Player (
    val type: PlayerType,
    val checkers: ArrayList<BoardPosition> = ArrayList()
)