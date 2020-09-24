package graphcolouring

import kotlin.test.assertEquals

fun main () {

    val courseFile = "car-f-92.crs"
    val studentFile = "car-f-92.stu"


    val courseGraph = CourseGraph(
        courseFile = "/home/user/Workspaces/Java/intellij/A_i/src/graphcolouring/solving_timetabling/ai_lab_class/${courseFile}",
        studentFile = "/home/user/Workspaces/Java/intellij/A_i/src/graphcolouring/solving_timetabling/ai_lab_class/${studentFile}"
    )

    courseGraph.processCourseFile()
    courseGraph.processStudentFile()

    for (item in courseGraph.courses) {
        assertEquals(item.value.studentCount, item.value.studentIds.size)
    }

    val solver = GraphColoringScheduler(courseGraph)
    solver.execute()


}