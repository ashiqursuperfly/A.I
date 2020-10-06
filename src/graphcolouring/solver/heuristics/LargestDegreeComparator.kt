package graphcolouring.heuristics

import graphcolouring.models.Course

class LargestDegreeComparator : Comparator<Course> {

    override fun compare(o1: Course, o2: Course): Int {

        val degree1 = o1.neighbours.size
        val degree2 = o2.neighbours.size
        return when {
            degree1 > degree2 -> {
                1
            }
            degree1 < degree2 -> {
                -1
            }
            else -> {
                0
            }
        }

    }
}