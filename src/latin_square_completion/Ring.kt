package latin_square_completion

class Ring(val size: Int) : Circular<Int> {
    private var state = 0

    override fun state() = state
    override fun inc() {state = (1 + state) % size}
    override fun isZero() = (state == 0)
    override fun hasNext() = (state != size - 1)

    init {
        assert(size > 0)
    }
}