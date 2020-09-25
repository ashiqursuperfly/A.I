package graphcolouring

import kotlin.test.assertEquals

fun main () {


    val courseFiles = arrayOf("car-s-91.crs", "car-f-92.crs", "kfu-s-93.crs", "tre-s-92.crs", "yor-f-83.crs")
    val studentFiles = arrayOf("car-s-91.stu", "car-f-92.stu", "kfu-s-93.stu", "tre-s-92.stu", "yor-f-83.stu")

    for (j in 0..10) {
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
            solver.execute()
            println("${courseFiles[i]},${solver.colorsUsed}")
        }
        println()
        println()
    }

}