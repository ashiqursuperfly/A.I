package graphcolouring.solver

import graphcolouring.CourseGraph
import graphcolouring.models.Course
import java.util.*
import kotlin.test.assertEquals

class PairSwapOperator(
    private val feasibleSolution: CourseGraph
) {

    init {
        GraphColouringUtils.assertZeroUnColoredVertices(feasibleSolution)
    }

    fun tryPairSwaps(n: Int, takeUpwardStepsOnly: Boolean) {
        for (i in 0..n) {
            tryPairSwap(takeUpwardStepsOnly)
        }
    }

    private fun tryPairSwap(takeUpwardStepsOnly: Boolean): Boolean {

        val randomPair: Pair<Course, Course> = feasibleSolution.getRandomCoursePair()


        if (isPairSwapValid(randomPair.first, randomPair.second)) {

            if (takeUpwardStepsOnly) {
                val prevPenalty = GraphColouringUtils.reCalculatePenalty(feasibleSolution)
                swapColors(randomPair.first, randomPair.second)
                val newPenalty = GraphColouringUtils.reCalculatePenalty(feasibleSolution)
                return if (prevPenalty > newPenalty) {
                    true
                } else {
                    // revert
                    swapColors(randomPair.first, randomPair.second)
                    false
                }
            }
            else {
                swapColors(randomPair.first, randomPair.second)
                return true
            }

        }

        return false


    }

    private fun isPairSwapValid(node1: Course, node2: Course): Boolean {

        if (node1.color == node2.color) {
            return false
        }

        val c1 = node1.neighbours.map { it.color }.contains(node2.color)
        val c2 = node2.neighbours.map { it.color }.contains(node1.color)

        if (c1 || c2) return false
        return true
    }

    private fun swapColors(n1: Course, n2: Course) {
        n1.color = n1.color xor n2.color
        n2.color = n2.color xor n1.color
        n1.color = n1.color xor n2.color
    }

}