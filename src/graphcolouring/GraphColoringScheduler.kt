package graphcolouring

import graphcolouring.Course.Companion.UNCOLORED
import graphcolouring.heuristics.BrelazComparator
import graphcolouring.heuristics.ConstructiveHeuristic
import graphcolouring.heuristics.LargestDegreeComparator
import java.util.*
import kotlin.collections.ArrayList

class GraphColoringScheduler(
    var graph: CourseGraph
) {
    var colorsUsed = 0

    fun executeConstructive(heuristic: ConstructiveHeuristic) {
        colorsUsed = 0

        val comparator = when(heuristic) {
            ConstructiveHeuristic.LARGEST_DEGREE_FIRST -> {
                LargestDegreeComparator()
            }
            ConstructiveHeuristic.BRELAZ_HIGHEST_COLOR_SATURATION -> {
                BrelazComparator()
            }
        }
        val priorityQueue = PriorityQueue(comparator.reversed())
        for (item in graph.courses) {
            priorityQueue.add(item.value)
        }

        while (priorityQueue.isNotEmpty()) {
            val nextToColor = priorityQueue.poll()
            val availableColors = getAvailableColors()

            for (item in nextToColor.neighbours) {
                if (item.color != UNCOLORED) availableColors.remove(item.color)
            }

            val selectedColor = if (availableColors.isEmpty()) {
                ++colorsUsed
            }
            else {
                availableColors[0]
            }

            nextToColor.color = selectedColor

            priorityQueue.add(nextToColor)
            priorityQueue.remove(nextToColor)

        }
    }

    private fun getAvailableColors () : ArrayList<Int> {
        return ArrayList(IntArray(colorsUsed) { i -> i }.asList())
    }

}