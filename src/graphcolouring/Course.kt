package graphcolouring

data class Course (
    val id: String,
    var studentCount: Int,
    val studentIds: HashSet<String> = HashSet()
)