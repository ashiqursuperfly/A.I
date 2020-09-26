package graphcolouring

import graphcolouring.models.Course
import graphcolouring.models.Student

class CourseGraph(
    private var courseFile: String,
    private var studentFile: String
) {
    var totalStudentCount = 0
    var courses = HashMap<String, Course>() // key:courseID value:Course
    var students = ArrayList<Student>() // key:courseID value:Course


    private fun createNextStudentId() : String {
        return (++totalStudentCount).toString()
    }

    private fun assignStudentToCourses(line: String) {
        val newStudentId = createNextStudentId()
        val studentCourses = line.split(' ')

        val student = Student(id = newStudentId)

            studentCourses.forEach {
                courses[it]?.apply {
                    student.courseIDs.add(this.id)
                    this.studentIds.add(newStudentId)
                    studentCourses.forEach { it2->
                        if (it != it2) {
                            courses[it2]?.let { it3 ->
                                this.neighbours.addUnique(it3)
                                it3.neighbours.addUnique(this)
                            }
                        }
                    }
                }
            }
            students.add(student)
    }

    private fun addCourse(line: String) {
        val t = line.split(' ')
        val courseID = t[0]
        val totalStudents = t[1].toInt()

        courses[courseID] = Course(id = courseID, studentCount = totalStudents)
    }

    fun processCourseFile() {
        FileUtil.readFileLineByLineUsingForEachLine(
            courseFile,
            ::addCourse
        )
    }

    fun processStudentFile() {
        FileUtil.readFileLineByLineUsingForEachLine(
            studentFile,
            ::assignStudentToCourses
        )
    }


}
