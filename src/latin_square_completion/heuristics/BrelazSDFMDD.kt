package latin_square_completion.heuristics

import latin_square_completion.LatinRowCompareData

class BrelazSDFMDD : Comparator<LatinRowCompareData> {

    override fun compare(o1: LatinRowCompareData, o2: LatinRowCompareData): Int {
        val sdf = SmallestDomainFirst()
        val c1 = sdf.calculateValue(o1)
        val c2 = sdf.calculateValue(o2)

        if (c1 != c2) {
            return c1.compareTo(c2)
        }

        val mdd = MaxDynamicDegree()

        return mdd.compare(o1, o2)
    }


}