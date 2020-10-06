package graphcolouring.solver

import graphcolouring.CourseGraph
import graphcolouring.models.Course
import java.util.*

class KempeChainInterchange(
    private val feasibleSolution: CourseGraph
) {
    init {
        GraphColouringUtils.assertZeroUnColoredVertices(feasibleSolution)
    }

    fun tryKempeChainInterChange() {
        val v = feasibleSolution.getRandomCourse(feasibleSolution.courses.map { it.key })
        val c2 = feasibleSolution.getRandomCourse(v.neighbours.map { it.id }).color

        applyKempe(v, v.color, c2)

    }

    private fun applyKempe(course: Course, c1: Int, c2: Int) {
        val q = LinkedList<Course>()
        q.push(start)

        while (!q.isEmpty()) {
            val popped = q.poll()

            popped.neighbours.forEach {
                if (it.color)
            }
        }
    }
}