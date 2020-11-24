package latin_square_completion

interface Circular<T> : Iterable<T> {
    fun state(): T
    fun inc()
    fun isZero(): Boolean   // `true` in exactly one state
    fun hasNext(): Boolean  // `false` if the next state `isZero()`

    override fun iterator() : Iterator<T> {
        return object : Iterator<T> {
            var started = false

            override fun next(): T {
                if(started)
                    inc()
                else
                    started = true

                return state()
            }

            override fun hasNext() = this@Circular.hasNext()
        }
    }
}
