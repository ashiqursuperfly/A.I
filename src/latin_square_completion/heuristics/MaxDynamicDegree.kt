package latin_square_completion.heuristics

import latin_square_completion.Constants
import latin_square_completion.LatinRowCompareData

class MaxDynamicDegree : Comparator<LatinRowCompareData> {

   fun calculateValue(latinRowCompareData: LatinRowCompareData): Int {
        val row = latinRowCompareData.latinSquare.data[latinRowCompareData.rowIdx]
        val rowHashSet = latinRowCompareData.latinSquare.rowHashSets[latinRowCompareData.rowIdx]
        var sum = 0
        for ((j, item) in row.withIndex()) {
            if (item == Constants.EMPTY) {
                sum += (2*row.size - rowHashSet.size - latinRowCompareData.latinSquare.colHashSets[j].size)
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