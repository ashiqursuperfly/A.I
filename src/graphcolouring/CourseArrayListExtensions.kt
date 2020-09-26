package graphcolouring

import graphcolouring.models.Course

fun ArrayList<Course>.addUnique(course: Course) {
    if (!this.contains(course))
        this.add(course)
}