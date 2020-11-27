package utils.permutation

class Permutation(
    val data: ArrayList<Int>
) {
    val results = ArrayList<ArrayList<Int>>()

    fun calculatePermute(): ArrayList<ArrayList<Int>> {
        permute(data, 0, data.size-1)
        return results
    }

    fun permute(str: ArrayList<Int>, l: Int, r: Int) {
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

    private fun swap(a: ArrayList<Int>, i: Int, j: Int): ArrayList<Int> {
        val temp: Int
        val charArray = ArrayList(a)
        temp = charArray[i]
        charArray[i] = charArray[j]
        charArray[j] = temp
        return charArray
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val str = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
            val permutation = Permutation(data = str)
            permutation.calculatePermute()
            /*for (item in permutation.results) {
                println(item)
            }*/
        }
    }
}