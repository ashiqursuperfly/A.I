package loa

import java.lang.StringBuilder

data class LOA (
    var checkerCount: Int = 0,
    val path: ArrayList<BoardPosition> = ArrayList()
) {
    fun isBoardPositionInLOA(pos: BoardPosition): Boolean {
        return path.contains(pos)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LOA) return false

        if (path != other.path) return false

        return true
    }

    override fun hashCode(): Int {
        return path.hashCode()
    }

    override fun toString(): String {
        val sb = StringBuilder()

        for (item in path) sb.append('(').append(item.row).append(',').append(item.col).append(')').append('-')

        return "LOA(checkerCount=$checkerCount, path=$sb)"
    }


}