package graphcolouring.models

data class Student (
    val id: String,
    var courseIDs: HashSet<String> = HashSet()
)