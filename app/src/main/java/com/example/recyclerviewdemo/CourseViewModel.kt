package com.example.recyclerviewdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CourseViewModel : ViewModel(){
    var course: Course? = null
    var hole : MutableList<Hole> = mutableListOf()

    private var _name = MutableLiveData("")
    val name: LiveData<String>
        get() = _name

    fun updateCourse(name : String, location : String) {
        course = Course(name, hole, hole.size, calculatePar(), location,calculateDistance())
    }
    fun addHole(a :Int, b :Int) {
        hole.add(Hole(a, b))
    }
    fun clearCourse(){
        course = null
    }
    fun calculatePar() : Int{
        var par = 0
        for(each in hole) {
            par += each.par
        }
        return par
    }
    fun calculateDistance() : Int{
        var distance = 0
        for(each in hole) {
            distance += each.distance
        }
        return distance
    }
}