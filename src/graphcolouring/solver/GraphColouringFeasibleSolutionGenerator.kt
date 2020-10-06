package graphcolouring.solver

import graphcolouring.CourseGraph
import graphcolouring.heuristics.BrelazComparator
import graphcolouring.heuristics.ConstructiveHeuristic
import graphcolouring.heuristics.LargestDegreeComparator
import graphcolouring.heuristics.LargestEnrollmentComparator
import graphcolouring.models.Course
import graphcolouring.models.Course.Companion.UNCOLORED
import graphcolouring.solver.GraphColouringUtils.reCalculatePenalty
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class GraphColouringFeasibleSolutionGenerator(
    var graph: CourseGraph
) {
    var colorsUsed = 0

    fun solveConstructive(heuristic: ConstructiveHeuristic) {
        colorsUsed = 0

        val comparator = when (heuristic) {
            ConstructiveHeuristic.LARGEST_DEGREE_FIRST -> {
                LargestDegreeComparator()
            }
            ConstructiveHeuristic.BRELAZ_HIGHEST_COLOR_SATURATION -> {
                BrelazComparator()
            }
            ConstructiveHeuristic.LARGEST_ENROLLMENT -> {
                LargestEnrollmentComparator()
            }
        }
        val priorityQueue = PriorityQueue(comparator.reversed())

        graph.courses.forEach { (_, course) ->
            priorityQueue.add(course)
        }

        while (priorityQueue.isNotEmpty()) {
            val nextToColor = priorityQueue.poll()
            val availableColors = getAvailableColors()

            for (item in nextToColor.neighbours) {
                if (item.color != UNCOLORED) availableColors.remove(item.color)
            }

            val selectedColor = if (availableColors.isEmpty()) {
                ++colorsUsed
            } else {
                availableColors[0]
            }

            nextToColor.color = selectedColor

            priorityQueue.add(nextToColor)
            priorityQueue.remove(nextToColor)
        }
    }

    private fun getAvailableColors(): ArrayList<Int> {
        if (colorsUsed == 0) return ArrayList()
        return ArrayList(IntArray(colorsUsed) { i -> i + 1 }.asList())
    }

}

