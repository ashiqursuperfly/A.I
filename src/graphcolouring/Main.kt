package graphcolouring

import graphcolouring.solver.heuristics.ConstructiveHeuristic
import graphcolouring.solver.GraphColouringFeasibleSolutionGenerator
import graphcolouring.solver.GraphColouringUtils as GCU
import graphcolouring.solver.KempeChainInterchange
import graphcolouring.solver.PairSwapOperator

fun main () {

    val courseFiles = arrayOf("car-s-91.crs", "car-f-92.crs", "kfu-s-93.crs", "tre-s-92.crs", "yor-f-83.crs")
    val studentFiles = arrayOf("car-s-91.stu", "car-f-92.stu", "kfu-s-93.stu", "tre-s-92.stu", "yor-f-83.stu")

    for (i in courseFiles.indices) {

        val courseGraph = CourseGraph(
            courseFile = "/home/user/Workspaces/Java/intellij/A_i/src/graphcolouring/solving_timetabling/ai_lab_class/${courseFiles[i]}",
            studentFile = "/home/user/Workspaces/Java/intellij/A_i/src/graphcolouring/solving_timetabling/ai_lab_class/${studentFiles[i]}"
        )

        courseGraph.processCourseFile()
        courseGraph.processStudentFile()

        val solver = GraphColouringFeasibleSolutionGenerator(courseGraph)
        solver.solveConstructive(ConstructiveHeuristic.BRELAZ_HIGHEST_COLOR_SATURATION)

        GCU.assertZeroUnColoredVertices(solver.graph)
        GCU.assertNoNeighbourHasParentColor(solver.graph)

        val kempe = KempeChainInterchange(solver.graph)
        kempe.kempeChainInterchangeFromAllVertices(false)
        kempe.tryRandomKempeChainInterChange(10000, true)


        val pairSwapper = PairSwapOperator(solver.graph)
        pairSwapper.tryPairSwaps(100000, true)


        println("${courseFiles[i]}, ${GCU.countColorsUsed(courseGraph)}, ${GCU.reCalculatePenalty(solver.graph)}")

        GCU.assertZeroUnColoredVertices(solver.graph)
        GCU.assertNoNeighbourHasParentColor(solver.graph)

        //GCU.createDetailedReport("${courseFiles[i].split('.')[0]}.sol",solver.graph)


    }




}
