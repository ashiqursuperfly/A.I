package graphcolouring

import graphcolouring.models.Course
import graphcolouring.models.Student

class CourseGraph(
    private var courseFile: String,
    private var studentFile: String
) {
    var totalStudentCount = 0
    var courses = HashMap<String, Course>() // key:courseID value:Course
    var students = ArrayList<Student>()

    private fun createNextStudentId() : String {
        return (++totalStudentCount).toString()
    }

    private fun assignStudentToCourses(line: String) {
        val newStudentId = createNextStudentId()
        val studentCourses = line.split(' ')

        val student = Student(id = newStudentId)

        studentCourses.forEach {
            if (it.isNotBlank()) {
                courses[it]!!.apply {
                    student.courseIDs.add(this.id)
                    this.studentIds.add(newStudentId)
                    studentCourses.forEach { it2 ->
                        if (this.id != it2) {
                            courses[it2]?.let { it3 ->
                                this.neighbours.addUnique(it3)
                                it3.neighbours.addUnique(this)
                            }
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

    fun getRandomCoursePair(): Pair<Course, Course> {
        var idx1 = -1
        var idx2 = -1
        do {
            idx1 = (Math.random() * Int.MAX_VALUE).toInt() % courses.size
            idx2 = (Math.random() * Int.MAX_VALUE).toInt() % courses.size
        } while (idx1 == idx2)

        //println("rand: ${idx1} ${idx2}")
        val nodes = courses.map {
            it.key
        }

        return Pair(courses[nodes[idx1]]!!, courses[nodes[idx2]]!!)

    }
}
