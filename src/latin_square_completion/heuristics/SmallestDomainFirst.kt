package latin_square_completion.heuristics

import latin_square_completion.Constants
import latin_square_completion.LatinRowCompareData

class SmallestDomainFirst : Comparator<LatinRowCompareData> {

    private fun calculateValue(latinRowCompareData: LatinRowCompareData): Int {
        val row = latinRowCompareData.latinSquare.data[latinRowCompareData.rowIdx]

        var sum = 0
        for ((j, item) in row.withIndex()) {
            if (item == Constants.EMPTY) {
                val hashSet = HashSet<Int>()
                hashSet.addAll(latinRowCompareData.latinSquare.rowHashSets[latinRowCompareData.rowIdx])
                hashSet.addAll(latinRowCompareData.latinSquare.colHashSets[j])

                sum += (row.size - hashSet.size)
            }
        }
        return sum
    }

    override fun compare(o1: LatinRowCompareData, o2: LatinRowCompareData): Int {
        val c1 = calculateValue(o1)
        val c2 = calculateValue(o2)

        return c1.compareTo(c2)
    }


}