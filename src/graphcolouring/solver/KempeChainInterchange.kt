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
        val start = feasibleSolution.getRandomCourse(feasibleSolution.courses.map { it.key })

    }

    /*fun applyKempe() {
        val q = LinkedList<Course>()
        q.push(start)

        while (!q.isEmpty()) {
            val popped = q.poll()

            popped.neighbours.forEach {
                if (it.color)
            }
        }
    }*/
}