package loa

import java.lang.StringBuilder

class LOAFactory {

    enum class LOAType {
        H, V, TL_BR, BL_TR
    }

    val all = HashMap<String, LOA>()

    private fun getKey(type: LOAType, center: BoardPosition) : ArrayList<Pair<Int, Int>> {

        val row = center.row
        val col = center.col

        val key = ArrayList<Pair<Int, Int>>()
        when (type) {
            LOAType.H -> {
                for (i in 0 until 8) {
                    key.add(Pair(row, i))
                }
            }
            LOAType.V -> {
                for (i in 0 until 8) {
                    key.add(Pair(i, col))
                }
            }
            LOAType.TL_BR -> {
                var tr = row
                var tc = col
                while (tr > 0 && tc > 0) {
                    tr--
                    tc--
                }
                while (tr < 8 && tc < 8) {
                    key.add(Pair(tr++, tc++))
                }
            }
            LOAType.BL_TR -> {
                var tr = row
                var tc = col
                while (tr < 7 && tc > 0) {
                    tr++
                    tc--
                }
                while (tr >= 0 && tc < 8) {
                    key.add(Pair(tr--, tc++))
                }

            }
        }

        return key
    }

    fun getOrCreate(type: LOAType, center: BoardPosition, state: State) : LOA {
        val coords = getKey(type, center)

        val data = all[coords.toString()]

        if (data != null) return data
        else {
            println("${type.name} : $coords ")
            val l = LOA()
            for (item in coords) {
                l.path.add(state.board[item.first][item.second])
            }
            all[coords.toString()] = l
            return l
        }

    }
}
