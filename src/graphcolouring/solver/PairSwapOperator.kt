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

    fun tryPairSwaps(n: Int) {
        val prevPenalty = GraphColouringUtils.reCalculatePenalty(feasibleSolution)
        for (i in 0..n) {
            if (tryPairSwap()) {
                val newPenalty = GraphColouringUtils.reCalculatePenalty(feasibleSolution)
                if (prevPenalty >= newPenalty) {
                    val input = Scanner(System.`in`)
                    println("Penalty Improved $prevPenalty -> $newPenalty !! Do You Want to Keep Trying ? (y/N)")
                    if (input.nextLine().trim().equals("y", true)) break
                }

            }
        }

    }

    private fun tryPairSwap(): Boolean {

        val randomPair: Pair<Course, Course> = feasibleSolution.getRandomCoursePair()


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

        if (c1) {
            assertEquals(node1.neighbours.find { it.color == node2.color }?.color, node2.color)
        }
        if (c2) {
            assertEquals(node2.neighbours.find { it.color == node1.color }?.color, node1.color)
        }

        if (c1 || c2) return false
        return true
    }

}