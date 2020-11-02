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

    fun tryRandomKempeChainInterChange(n: Int, takeUpwardStepsOnly: Boolean) {
        for (i in 0..n) {
            val v = feasibleSolution.getRandomCourse(feasibleSolution.courses.map { it.key })
            if (v.neighbours.size != 0) {
                val c2 = feasibleSolution.getRandomCourse(v.neighbours.map { it.id }).color
                applyKempe(v, v.color, c2, takeUpwardStepsOnly)
            }
        }
    }

    fun kempeChainInterchangeFromAllVertices(takeUpwardStepsOnly: Boolean) {
        feasibleSolution.courses.forEach {
            if (it.value.neighbours.size != 0) {
                val c2 = feasibleSolution.getRandomCourse(it.value.neighbours.map {it1 -> it1.id }).color
                applyKempe(it.value, it.value.color, c2, takeUpwardStepsOnly)
            }
        }
    }

    private fun applyKempe(v: Course, c1: Int, c2: Int, takeUpwardStepsOnly: Boolean): Boolean {

        val chain = bfs(v, c1, c2)

        if (takeUpwardStepsOnly) {
            val prevPenalty = GraphColouringUtils.reCalculatePenalty(feasibleSolution)
            performKempeInterchange(chain, c1, c2)
            val newPenalty = GraphColouringUtils.reCalculatePenalty(feasibleSolution)
            if (newPenalty > prevPenalty) {
                //revert
                performKempeInterchange(chain, c1, c2)
                assertEquals(prevPenalty.toInt(), GraphColouringUtils.reCalculatePenalty(feasibleSolution).toInt())
                return false
            }
            return true
        }
        else {
            performKempeInterchange(chain, c1, c2)
            return true
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

    private fun bfs(v: Course, c1: Int, c2:Int): ArrayList<Course> {
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

        return chain
    }
}