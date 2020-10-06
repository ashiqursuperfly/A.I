package graphcolouring.solver

import graphcolouring.CourseGraph
import graphcolouring.models.Course
import java.util.*
import kotlin.test.assertEquals

class KempeChainInterchange(
    private val feasibleSolution: CourseGraph
) {
    init {
        GraphColouringUtils.assertZeroUnColoredVertices(feasibleSolution)
    }

    fun tryKempeChainInterChange() {
        val v = feasibleSolution.getRandomCourse(feasibleSolution.courses.map { it.key })
        if (v.neighbours.size != 0) {
            val c2 = feasibleSolution.getRandomCourse(v.neighbours.map { it.id }).color
            val success = applyKempe(v, v.color, c2)
            /*if (success) {
                GraphColouringUtils.assertZeroUnColoredVertices(feasibleSolution)
                GraphColouringUtils.assertNoNeighbourHasParentColor(feasibleSolution)
                assertEquals(GraphColouringUtils.countColorsUsed(feasibleSolution) , 32)

            }*/
        }
    }

    private fun applyKempe(v: Course, c1: Int, c2: Int): Boolean {
        val prevPenalty = GraphColouringUtils.reCalculatePenalty(feasibleSolution)

        val isMarked = HashSet<String>()
        val chain = ArrayList<Course>()

        val q : Queue<Course> = LinkedList()
        isMarked.add(v.id)
        q.add(v)

        while (!q.isEmpty()) {
            val popped = q.poll()
            chain.add(popped)

            val allowedNeighborColor = if (popped.color == c1) c2 else c1
            popped.neighbours.filter {
                it.color == allowedNeighborColor && !isMarked.contains(it.id)
            }.forEach {
                q.add(it)
                isMarked.add(it.id)
            }
        }
        performKempeInterchange(chain, c1, c2)

        val newPenalty = GraphColouringUtils.reCalculatePenalty(feasibleSolution)

        return if (newPenalty > prevPenalty) {
            //revert
            performKempeInterchange(chain, c1, c2)
            assertEquals(prevPenalty.toInt(), GraphColouringUtils.reCalculatePenalty(feasibleSolution).toInt())
            false
        }
        else {
            if(newPenalty < prevPenalty)println("applyKempe(${v.id}, ${c1}, $c2) Penalty: $newPenalty" )
            true
        }

    }

    private fun performKempeInterchange(chain: ArrayList<Course>, c1: Int, c2: Int) {
        for (item in chain) {
            assert(item.color==c1 || item.color==c2)
            item.color = when (item.color) {
                c1 -> c2
                c2 -> c1
                else -> {
                    throw Exception("ERROR: Cannot Have ${item.color} in a kempe chain of $c1 & $c2!!")
                }
            }
        }
    }
}