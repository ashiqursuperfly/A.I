package graphcolouring

class GraphColouringExamScheduler(
    private var courseFile: String,
    private var studentFile: String
) {
    var totalStudentCount = 0
    lateinit var courses: HashMap<String,Course>

    private fun createNextStudentId() : String {
        return (++totalStudentCount).toString()
    }

    private fun assignStudentToCourses(line: String) {
        val newStudentId = createNextStudentId()
        val studentCourses = line.split(' ')

        for (item in studentCourses) {
            courses[item]?.apply {
                this.studentIds.add(newStudentId)
            }
        }
    }

    private fun addCourse(line: String) {
        val t = line.split(' ')
        val courseID = t[0]
        val totalStudents = t[1].toInt()

        if (!::courses.isInitialized) {
            courses = HashMap()
        }

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
