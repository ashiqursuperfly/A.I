package graphcolouring

fun ArrayList<Course>.addUnique(course: Course) {
    if (!this.contains(course))
        this.add(course)
}