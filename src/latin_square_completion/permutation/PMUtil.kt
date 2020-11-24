package latin_square_completion.permutation

class Permutation(
    val data: String
) {

    val results = ArrayList<String>()

    fun calculatePermute(): ArrayList<String> {
        permute(data, 0, data.length-1)
        return results
    }

    fun permute(str: String, l: Int, r: Int) {
        var temp = str
        if (l == r) results.add(temp)
        else {
            for (i in l..r) {
                temp = swap(temp, l, i)
                permute(temp, l + 1, r)
                temp = swap(temp, l, i)
            }
        }
    }

    /**
     * Swap Characters at position
     * @param a string value
     * @param i position 1
     * @param j position 2
     * @return swapped string
     */
    private fun swap(a: String, i: Int, j: Int): String {
        val temp: Char
        val charArray = a.toCharArray()
        temp = charArray[i]
        charArray[i] = charArray[j]
        charArray[j] = temp
        return String(charArray)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val str = "435"
            val permutation = Permutation(str)
            permutation.calculatePermute()
            for (item in permutation.results) {
                println(item)
            }
        }
    }
}