package graphcolouring.solver

import graphcolouring.CourseGraph
import graphcolouring.models.Course
import kotlin.math.abs
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

object GraphColouringUtils {

    fun reCalculatePenalty(graph: CourseGraph): Float {
        val cost = arrayOf(Int.MAX_VALUE, 16, 8, 4, 2, 1)

        var penalty = 0
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

                    if (diff == 0) {
                        println(studentSchedule)
                        assertNotEquals(0, diff)
                    }
                    if (diff >= cost.size) continue
                    penalty += cost[diff]

                }
            }

        }

        return (penalty / graph.students.size.toFloat())
    }

    fun assertZeroUnColoredVertices(graph: CourseGraph) {
        val uncolored = graph.courses.filter {
            it.value.color == Course.UNCOLORED
        }.size
        assertEquals(0, uncolored)
    }

    fun assertNoNeighbourHasParentColor(graph: CourseGraph) {
        graph.courses.forEach { (_, course) ->
            course.neighbours.forEach {
                assertNotEquals(course.color, it.color)
            }
        }
    }
    fun countColorsUsed(graph: CourseGraph): Int {
        val colors = ArrayList<Int>()
        graph.courses.forEach { (_, course) ->
           if (!colors.contains(course.color))
               colors.add(course.color)
        }
        return colors.size
    }

    fun createDetailedReport(graph: CourseGraph, colorsUsed: Int) {
        var total = 0
        for (x in 1..colorsUsed) {
            val colorClass = graph.courses.filter { it.value.color == x  }

            total += colorClass.size

            println("$x ${colorClass.map { it.key }} ${colorClass.size}")

        }

        assertEquals(total, graph.courses.size)

    }

}