package graphcolouring.solver

import graphcolouring.CourseGraph
import graphcolouring.models.Course
import java.lang.Exception
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class KempeChainInterchange(
    private val feasibleSolution: CourseGraph
) {
    init {
        GraphColouringUtils.assertZeroUnColoredVertices(feasibleSolution)
    }

    fun tryKempeChainInterChange() {
        val v = feasibleSolution.getRandomCourse(feasibleSolution.courses.map { it.key })
        val c2 = feasibleSolution.getRandomCourse(v.neighbours.map { it.id }).color
        println("applyKempe(${v.id}, ${v.color}, $c2)")
        applyKempe(v, v.color, c2)

    }

    private fun applyKempe(course: Course, c1: Int, c2: Int) {

        val isMarked = HashSet<String>()
        val chain = ArrayList<Course>()

        val q : Queue<Course> = LinkedList()
        isMarked.add(course.id)
        q.add(course)

        while (!q.isEmpty()) {
            val popped = q.poll()
            chain.add(popped)

            val allowedNeighborColor = if (popped.color == c1) c2 else c1
            /*val kempeNeighbors = popped.neighbours.filter {
                it.color == allowedNeighborColor && !isMarked.contains(it.id)
            }

            kempeNeighbors.forEach {
                q.add(it)
                isMarked.add(it.id)
            }*/

            for (item in popped.neighbours) {
                assertNotEquals(popped.color, item.color)
                if (item.color == allowedNeighborColor && !isMarked.contains(item.id)) {
                    q.add(item)
                    isMarked.add(item.id)
                }
            }
        }

        for (item in chain) {
            assert(item.color==c1 || item.color==c2)
            item.color = when (item.color) {
                c1 -> c2
                c2 -> c1
                else -> {
                    throw Exception("!!ERROR!!")
                }
            }
        }
        val penalty = GraphColouringUtils.reCalculatePenalty(feasibleSolution)
        println("Updated Penalty: $penalty")
    }
}