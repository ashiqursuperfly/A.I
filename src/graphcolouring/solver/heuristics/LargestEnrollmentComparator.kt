package graphcolouring.heuristics

import graphcolouring.models.Course

class LargestEnrollmentComparator : Comparator<Course> {

    override fun compare(o1: Course, o2: Course): Int {

        val sc1 = o1.studentCount
        val sc2 = o2.studentCount
        return when {
            sc1 > sc2 -> {
                1
            }
            sc1 < sc2 -> {
                -1
            }
            else -> {
                0
            }
        }

    }
}