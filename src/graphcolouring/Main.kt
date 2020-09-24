package graphcolouring

import kotlin.test.assertEquals

fun main () {

    val solver = GraphColouringExamScheduler(
        courseFile = "/home/user/Workspaces/Java/intellij/A_i/src/graphcolouring/solving_timetabling/ai_lab_class/yor-f-83.crs",
        studentFile = "/home/user/Workspaces/Java/intellij/A_i/src/graphcolouring/solving_timetabling/ai_lab_class/yor-f-83.stu"
    )

    solver.processCourseFile()
    solver.processStudentFile()

    for (item in solver.courses) {
        assertEquals(item.value.studentCount, item.value.studentIds.size)
    }
    println(solver.totalStudentCount)


}