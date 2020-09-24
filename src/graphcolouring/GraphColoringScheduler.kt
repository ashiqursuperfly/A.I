package graphcolouring

import graphcolouring.Course.Companion.UNCOLORED
import graphcolouring.heuristics.BrelazComparator
import java.util.*
import kotlin.collections.ArrayList

class GraphColoringScheduler(
    var graph: CourseGraph
) {

    private lateinit var priorityQueue: PriorityQueue<Course>

    fun execute() {
        var colorsUsed = 0
        priorityQueue = PriorityQueue(BrelazComparator())

        for (item in graph.courses) {
            priorityQueue.add(item.value)
        }

        while (priorityQueue.isNotEmpty()) {
            val nextToColor = priorityQueue.poll()

            val availableColors = getAvailableColors(colorsUsed)

            for (item in nextToColor.neighbours) {
                if (item.color != UNCOLORED) availableColors.remove(item.color)
            }

            val selectedColor = if (availableColors.isEmpty()) {
                ++colorsUsed
            }
            else {
                availableColors[0]
            }
            println(colorsUsed)
            nextToColor.color = selectedColor

            priorityQueue.add(nextToColor)
            priorityQueue.remove(nextToColor)

        }

    }

    private fun getAvailableColors (colorsUsed: Int) : ArrayList<Int> {
        return ArrayList(IntArray(colorsUsed) { i -> i }.asList())
    }

}