package graphcolouring

import graphcolouring.heuristics.ConstructiveHeuristic
import graphcolouring.models.Course.Companion.UNCOLORED
import kotlin.test.assertEquals

fun main () {

    val courseFiles = arrayOf("car-s-91.crs", "car-f-92.crs", "kfu-s-93.crs", "tre-s-92.crs", "yor-f-83.crs")
    val studentFiles = arrayOf("car-s-91.stu", "car-f-92.stu", "kfu-s-93.stu", "tre-s-92.stu", "yor-f-83.stu")

    for (j in 0..5) {
        for (i in courseFiles.indices) {

            val courseGraph = CourseGraph(
                courseFile = "/home/user/Workspaces/Java/intellij/A_i/src/graphcolouring/solving_timetabling/ai_lab_class/${courseFiles[i]}",
                studentFile = "/home/user/Workspaces/Java/intellij/A_i/src/graphcolouring/solving_timetabling/ai_lab_class/${studentFiles[i]}"
            )

            courseGraph.processCourseFile()
            courseGraph.processStudentFile()

            for (item in courseGraph.courses) {
                assertEquals(item.value.studentCount, item.value.studentIds.size)
            }

            val solver = GraphColoringScheduler(courseGraph)
            solver.executeConstructive(ConstructiveHeuristic.BRELAZ_HIGHEST_COLOR_SATURATION)

            val uncolored = solver.graph.courses.filter {
                it.value.color == UNCOLORED
            }.size
            assertEquals(0, uncolored)

            println("${courseFiles[i]}, ${solver.colorsUsed}, ${solver.penalty}")
        }

        println()
        println()
    }

}