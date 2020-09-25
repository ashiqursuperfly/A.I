package graphcolouring

import graphcolouring.Course.Companion.UNCOLORED
import graphcolouring.heuristics.BrelazComparator
import java.util.*
import kotlin.collections.ArrayList

class GraphColoringScheduler(
    var graph: CourseGraph
) {
    var colorsUsed = 0

    fun execute() {
        colorsUsed = 0
        val priorityQueue = PriorityQueue(BrelazComparator().reversed())

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
        val colors = ArrayList(IntArray(colorsUsed) { i -> i }.asList())
        colors.shuffle()
        return colors
    }

}