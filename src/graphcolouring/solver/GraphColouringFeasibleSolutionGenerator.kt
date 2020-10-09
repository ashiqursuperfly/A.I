package graphcolouring.solver

import graphcolouring.CourseGraph
import graphcolouring.solver.heuristics.BrelazComparator
import graphcolouring.solver.heuristics.ConstructiveHeuristic
import graphcolouring.solver.heuristics.LargestDegreeComparator
import graphcolouring.solver.heuristics.LargestEnrollmentComparator
import graphcolouring.models.Course.Companion.UNCOLORED
import java.util.*
import kotlin.collections.ArrayList

class GraphColouringFeasibleSolutionGenerator(
    var graph: CourseGraph
) {
    private var colorsUsed : Int = getInitialColors()

    fun getColorsUsed() : Int {
        return colorsUsed + 1
    }

    private fun getInitialColors(): Int {
        return (graph.students.size/graph.courses.size)
    }

    fun solveConstructive(heuristic: ConstructiveHeuristic) {

        colorsUsed = getInitialColors()

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
        return ArrayList(IntArray(colorsUsed) { i -> i }.asList())
    }

}

