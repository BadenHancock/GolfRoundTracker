package com.example.recyclerviewdemo

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.recyclerviewdemo.databinding.FragmentCourseSelectorBinding

class CourseSelectorFragment : Fragment() {

    private var _binding: FragmentCourseSelectorBinding? = null
    private val binding get() = _binding!!
    val courseViewModel: CourseViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCourseSelectorBinding.inflate(inflater, container, false)

        val rootView = binding.root
        val ccsFalls: List<Hole> = listOf(
            Hole(5, 520), Hole(3, 160),
            Hole(4, 350), Hole(4, 400), Hole(5, 480),
            Hole(4, 450), Hole(4, 420), Hole(3, 150), Hole(4, 450)
        )
        val ccsPines: List<Hole> = listOf(
            Hole(4, 400), Hole(4, 420), Hole(3, 160), Hole(5, 520),
            Hole(3, 220), Hole(4, 460), Hole(4, 420), Hole(4, 280),
            Hole(4, 320)
        )
        val WyomingValley: List<Hole> = listOf(
            Hole(5, 480), Hole(4, 320), Hole(4, 380), Hole(4, 300),
            Hole(3, 160), Hole(4, 390), Hole(3, 140), Hole(4, 340),
            Hole(4, 320), Hole(4, 280), Hole(5, 500), Hole(5, 510), Hole(3, 160),
            Hole(4, 320), Hole(3, 200), Hole(4, 320), Hole(4, 320), Hole(4, 320)
        )


        val courses = mutableListOf(
            Course("Country Club of Scranton (Falls)", ccsFalls, 9, 36, "Scranton, Pennsylvania", 3600),
            Course("Country Club of Scranton (Pines)", ccsPines, 9, 35,"Scranton, Pennsylvania", 3600),
            Course("Wyoming Valley Country Club", WyomingValley, 18, 71,"Wyoming Valley, Pennsylvania", 6700)
        )
        var myAdapter = CourseAdapter(courses)
        binding.EnterButton.setOnClickListener() {
            val course : Course? = (courseViewModel.course as? Course)
            if( course != null) {
                courses.add(course)
                myAdapter.notifyDataSetChanged()
                courseViewModel.clearCourse()
            }
        }

        binding.button2.setOnClickListener() {
            AlertDialog.Builder(requireContext())
                .setTitle("Select Holes")
                .setMessage("Choose the number of holes")
                .setPositiveButton("9 Holes") {_, _ ->
                    val action = CourseSelectorFragmentDirections.actionCourseSelectorFragmentToCourseCreatorFragment(9)
                    rootView.findNavController().navigate(action)
                }
                .setNegativeButton("18 Holes") {_, _ ->
                    val action = CourseSelectorFragmentDirections.actionCourseSelectorFragmentToCourseCreatorFragment(18)
                    rootView.findNavController().navigate(action)
                }
                .show()


        }
        binding.recyclerView.adapter = myAdapter
        return rootView
    }
}