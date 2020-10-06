package graphcolouring

import graphcolouring.heuristics.ConstructiveHeuristic
import graphcolouring.solver.GraphColouringFeasibleSolutionGenerator
import graphcolouring.solver.GraphColouringUtils
import graphcolouring.solver.KempeChainInterchange
import graphcolouring.solver.PairSwapOperator

fun main () {

    val courseFiles = arrayOf("yor-f-83.crs")//"car-s-91.crs", "car-f-92.crs", "kfu-s-93.crs", "tre-s-92.crs", "yor-f-83.crs")
    val studentFiles = arrayOf("yor-f-83.stu")//"car-s-91.stu", "car-f-92.stu", "kfu-s-93.stu", "tre-s-92.stu", "yor-f-83.stu")

    for (i in courseFiles.indices) {

        val courseGraph = CourseGraph(
            courseFile = "/home/user/Workspaces/Java/intellij/A_i/src/graphcolouring/solving_timetabling/ai_lab_class/${courseFiles[i]}",
            studentFile = "/home/user/Workspaces/Java/intellij/A_i/src/graphcolouring/solving_timetabling/ai_lab_class/${studentFiles[i]}"
        )

        courseGraph.processCourseFile()
        courseGraph.processStudentFile()


        val solver = GraphColouringFeasibleSolutionGenerator(courseGraph)
        solver.solveConstructive(ConstructiveHeuristic.BRELAZ_HIGHEST_COLOR_SATURATION)

        GraphColouringUtils.assertZeroUnColoredVertices(solver.graph)
        val penalty = GraphColouringUtils.reCalculatePenalty(solver.graph)

        println("${courseFiles[i]}, ${solver.colorsUsed}, $penalty ${solver.graph.courses.size}")

        /*val pairSwapper = PairSwapOperator(solver.graph)
        pairSwapper.tryPairSwaps(5)
        */

        val kempe = KempeChainInterchange(solver.graph)

        kempe.tryKempeChainInterChange()



    }




}