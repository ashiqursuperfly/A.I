package latin_square_completion

import java.util.*

class Permutations(N: Int) : IntCombinations(N) {
    override val state = mutableListOf<Ring>()

    init {
        for(i in N downTo 1) {
            state += Ring(i)
        }
    }

    override fun state(): List<Int> {
        val items = (0..size - 1).toCollection(LinkedList())
        return state.map {ring -> items.removeAt(ring.state())}
    }
}