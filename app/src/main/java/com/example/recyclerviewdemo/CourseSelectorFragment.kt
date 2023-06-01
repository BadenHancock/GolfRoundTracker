package com.example.recyclerviewdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.recyclerviewdemo.databinding.FragmentCourseSelectorBinding

class CourseSelectorFragment : Fragment() {

    private var _binding: FragmentCourseSelectorBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCourseSelectorBinding.inflate(inflater, container, false)
        val rootView = binding.root
        val ccsFalls: List<Hole> = listOf(
            Hole(520, 5), Hole(160, 3),
            Hole(350, 4), Hole(400, 4), Hole(480, 5),
            Hole(450, 4), Hole(420, 4), Hole(150, 3), Hole(450, 4)
        )
        val ccsPines: List<Hole> = listOf(
            Hole(400, 4), Hole(420, 4), Hole(160, 3), Hole(520, 5),
            Hole(220, 3), Hole(460, 4), Hole(420, 4), Hole(280, 4),
            Hole(340, 4)
        )
        val WyomingValley: List<Hole> = listOf(
            Hole(480, 5), Hole(320, 4), Hole(380, 4), Hole(300, 4),
            Hole(160, 3), Hole(390, 4), Hole(140, 3), Hole(340, 4),
            Hole(320, 4), Hole(280, 4), Hole(500, 5), Hole(510, 5), Hole(160, 3),
            Hole(320, 4), Hole(200, 3), Hole(320, 4), Hole(320, 4), Hole(320, 4)
        )


        val courses = mutableListOf(
            Course("Country Club of Scranton (Falls)", ccsFalls, 9, 36),
            Course("Country Club of Scranton (Pines)", ccsPines, 9, 35),
            Course("Wyoming Valley Country Club", WyomingValley, 18, 71)
        )

        binding.button2.setOnClickListener() {
            val action = CourseSelectorFragmentDirections.actionCourseSelectorFragmentToCourseCreatorFragment()
            rootView.findNavController().navigate(action)
        }

        val navController = findNavController()

        // Observe the result key for changes
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Bundle>("resultKey")?.observe(
            viewLifecycleOwner
        ) { result ->
            val resultObject = result?.getParcelable<Course>("resultObjectKey")

            if (resultObject != null) {
                courses.add(resultObject)
            }
        }

        val myAdapter = CourseAdapter(courses)
        binding.recyclerView.adapter = myAdapter
        return rootView
    }
}