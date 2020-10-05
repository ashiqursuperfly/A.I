package graphcolouring

import graphcolouring.heuristics.BrelazComparator
import graphcolouring.heuristics.ConstructiveHeuristic
import graphcolouring.heuristics.LargestDegreeComparator
import graphcolouring.heuristics.LargestEnrollmentComparator
import graphcolouring.models.Course
import graphcolouring.models.Course.Companion.UNCOLORED
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class GraphColoringScheduler(
    var graph: CourseGraph
) {
    var colorsUsed = 0

    var penalty = 0.0f

    private val cost = arrayOf (
        Int.MAX_VALUE, 16, 8, 4, 2, 1
    )

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

    fun reCalculatePenalty() {
        var cost = 0
        graph.students.forEach { student ->

            val studentSchedule = arrayListOf<Int>()

            student.courseIDs.forEach { courseID ->
                studentSchedule.add(graph.courses[courseID]!!.color)
            }

            if (studentSchedule.size > 1) {

                studentSchedule.sort()
                //println(studentSchedule)

                for (idx in studentSchedule.indices) {
                    if (idx == studentSchedule.size - 2) break
                    val nextExam = studentSchedule[idx]
                    val nextNextExam = studentSchedule[idx + 1]

                    val diff = abs(nextNextExam - nextExam)

                    assertNotEquals(diff, 0)

                    if (diff >= this.cost.size) continue
                    cost += this.cost[diff]

                }
            }

        }

        penalty = (cost / graph.students.size.toFloat())
    }

    fun tryPairSwaps(n: Int) {
        for (i in 0..n) {
            if (tryPairSwap()) {
                val prevPenalty = penalty
                reCalculatePenalty()

                if (prevPenalty > penalty) {
                    val input = Scanner(System.`in`)
                    println("Penalty Improved !! Do You Want to Keep Trying ? (y/N)")
                    if (input.nextLine().trim().equals("y", true)) break
                }

            }
        }

    }


    private fun tryPairSwap(): Boolean {

        val randomPair: Pair<Course, Course> = graph.getRandomCoursePair()

        if (isPairSwapValid(randomPair.first, randomPair.second)) {

            println("Before ${randomPair.first.id},${randomPair.first.color} ${randomPair.second.id},${randomPair.second.color}")
            randomPair.first.color = randomPair.first.color xor randomPair.second.color
            randomPair.second.color = randomPair.second.color xor randomPair.first.color
            randomPair.first.color = randomPair.first.color xor randomPair.second.color
            println("After ${randomPair.first.id},${randomPair.first.color} ${randomPair.second.id},${randomPair.second.color}")

            return true
        }

        return false


    }

    private fun isPairSwapValid(node1: Course, node2: Course): Boolean {

        if (node1.color == node2.color) {
            return false
        }

        val c1 = node1.neighbours.map { it.color }.contains(node2.color)
        val c2 = node2.neighbours.map { it.color }.contains(node1.color)

        //println(node1)
        //println(node2)

        if (c1 || c2) return false
        return true
    }

    fun assertZeroColoredVertices() {
        val uncolored = graph.courses.filter {
            it.value.color == UNCOLORED
        }.size
        assertEquals(0, uncolored)
    }

    fun createDetailedReport() {
        var total = 0
        for (x in 1..colorsUsed) {
            val colorClass = graph.courses.filter { it.value.color == x  }

            total += colorClass.size

            println("$x ${colorClass.map { it.key }} ${colorClass.size}")

        }

        assertEquals(total, graph.courses.size)

    }


}

