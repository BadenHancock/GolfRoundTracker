package com.example.recyclerviewdemo
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewdemo.databinding.ListItemLayoutCourseBinding


class CourseViewHolder(val binding: ListItemLayoutCourseBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentCourse: Course

    init {
        binding.root.setOnClickListener { view ->
            Toast.makeText(
                view.context, currentCourse.place + " clicked!",
                Toast.LENGTH_SHORT
            ).show()
            val action = CourseSelectorFragmentDirections.actionCourseSelectorFragmentToCreateRoundFragment(currentCourse)
            binding.root.findNavController().navigate(action)
        }
    }

    fun bindRound(Course: Course) {
        currentCourse = Course
        binding.textViewCourseName.text = currentCourse.place.toString()
        binding.textViewTotalPar.text = "${currentCourse.numOfHoles} Holes"
        binding.textViewYardage.text = currentCourse.totalDistance.toString() + " Yards"
        binding.textViewCourseLocation.text = currentCourse.location.toString()
    }
}