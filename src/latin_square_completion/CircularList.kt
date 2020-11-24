package latin_square_completion

abstract class CircularList<E, H: Circular<E>>(val size: Int) : Circular<List<E>> {
    protected abstract val state: List<H>  // state.size == size

    override fun inc() {
        state.forEach {
            it.inc()
            if(! it.isZero()) return
        }
    }

    override fun isZero() = state.all {it.isZero()}
    override fun hasNext() = state.any {it.hasNext()}
}
