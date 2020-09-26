package graphcolouring

import graphcolouring.heuristics.BrelazComparator
import graphcolouring.heuristics.ConstructiveHeuristic
import graphcolouring.heuristics.LargestDegreeComparator
import graphcolouring.models.Course.Companion.UNCOLORED
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class GraphColoringScheduler(
    var graph: CourseGraph
) {
    var colorsUsed = 0
    var penalty = 0.0f
    val COST = arrayOf(
        -1, 16, 8, 4, 2, 1
    )

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
        calculatePenalty()
    }

    private fun getAvailableColors () : ArrayList<Int> {
        return ArrayList(IntArray(colorsUsed) { i -> i }.asList())
    }

    private fun calculatePenalty() {
        var cost = 0
        graph.students.forEach { student ->
            val studentSchedule = arrayListOf<Int>()
            student.courseIDs.forEach { courseID ->
                studentSchedule.add(graph.courses[courseID]!!.color)
            }
            if (studentSchedule.size > 1) {

                studentSchedule.sort()
                // println(studentSchedule)

                for (idx in studentSchedule.indices) {
                    if (idx == studentSchedule.size - 2) break
                    val nextExam = studentSchedule[idx]
                    val nextNextExam = studentSchedule[idx + 1]

                    val diff = abs(nextNextExam - nextExam)

                    assertNotEquals(diff, 0)

                    if (diff >= COST.size) continue
                    cost += COST[abs(nextNextExam - nextExam)]

                }
            }

        }

        penalty = (cost / graph.students.size.toFloat())
    }

}