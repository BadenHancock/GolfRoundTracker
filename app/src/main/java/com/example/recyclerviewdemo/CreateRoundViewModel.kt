package com.example.recyclerviewdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CreateRoundViewModel : ViewModel(){
    var scores : MutableList<Int> = mutableListOf()
    fun addScore(a : Int) {
        scores.add(a)
    }
    fun clearScores() {
        scores = mutableListOf()
    }
}