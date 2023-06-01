package com.example.recyclerviewdemo

class CourseViewModel {
    var course: Course? = null
    var hole : MutableList<Hole> = mutableListOf()

    private var _distance = 0
    val distance: Int
        get() = _distance
    private var _par = 0
    val par : Int
        get() = _par

    fun updateCourse(holes: List<Hole>, numOfHoles: Int, totalPar: Int) {
        course = Course("Some Place", holes, numOfHoles, totalPar)
    }
    fun addHole() {
        hole.add(Hole(par, distance))
    }
    fun clearHoles(){

    }
}