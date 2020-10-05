package graphcolouring.models

data class Course (
        val id: String,
        var studentCount: Int,
        var color: Int = UNCOLORED,
        val studentIds: HashSet<String> = HashSet(),
        val neighbours: ArrayList<Course> = ArrayList()
) {


    companion object {
        const val UNCOLORED = 0
    }

    override fun toString(): String {
        return "Course(id='$id', color=$color}})"
    }


}