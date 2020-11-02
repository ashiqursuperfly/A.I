package graphcolouring.solver

import graphcolouring.CourseGraph
import graphcolouring.FileUtil
import graphcolouring.models.Course
import graphcolouring.models.Course.Companion.UNCOLORED
import java.lang.StringBuilder
import kotlin.math.abs
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

object GraphColouringUtils {

    /*fun reCalculatePenalty(graph: CourseGraph): Float {
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
    }*/

    fun reCalculatePenalty(graph: CourseGraph): Float {
        val cost = arrayOf(Int.MAX_VALUE, 16, 8, 4, 2, 1)

        var penalty = 0
        graph.students.forEach { student ->

            val studentSchedule = arrayListOf<Int>()

            student.courseIDs.forEach { courseID ->
                studentSchedule.add(graph.courses[courseID]!!.color)
            }

            if (studentSchedule.size > 1) {

                //println(studentSchedule)

                for (i1 in 0 until studentSchedule.size-1) {
                    for (i2 in (i1+1) until studentSchedule.size) {
                        //println("$i1 $i2")
                        if (i1 != i2) {
                            val exam1 = studentSchedule[i1]
                            val exam2 = studentSchedule[i2]

                            val diff = abs(exam1 - exam2)
                            if (diff == 0) {
                                println("Error In Schedule: $studentSchedule")
                                assertNotEquals(0, diff)
                            }
                            if (diff < cost.size) {
                                penalty += cost[diff]
                            }
                        }
                    }
                }
            }

        }

        return (penalty / graph.students.size.toFloat())
    }

    fun assertZeroUnColoredVertices(graph: CourseGraph) {
        val uncolored = graph.courses.filter {
            it.value.color == UNCOLORED
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
            assertNotEquals(UNCOLORED, course.color)
           if (!colors.contains(course.color))
               colors.add(course.color)
        }
        return colors.size
    }

    fun createDetailedReport(fileName: String, graph: CourseGraph) {
        val sb = StringBuilder()
        for (item in graph.courses){
            sb.append("${item.key.toInt()} ${item.value.color}").append('\n')
        }

        FileUtil.writeToFile(fileName, sb.toString())
    }

}