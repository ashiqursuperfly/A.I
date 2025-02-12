package graphcolouring.solver.heuristics

import graphcolouring.models.Course
import graphcolouring.models.Course.Companion.UNCOLORED

class BrelazComparator : Comparator<Course> {

    override fun compare(o1: Course, o2: Course): Int {

        val saturation1 = o1.neighbours.map {
            it.color
        }.distinct().filter {
            it != UNCOLORED
        }.size

        val saturation2 = o2.neighbours.map {
            it.color
        }.distinct().filter {
            it != UNCOLORED
        }.size

        when {
            saturation1 > saturation2 -> {
                return 1
            }
            saturation1 < saturation2 -> {
                return -1
            }
            else -> {
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

    }
}
