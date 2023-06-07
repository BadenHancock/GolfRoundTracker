package com.example.recyclerviewdemo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewdemo.databinding.FragmentCourseCreatorBinding
import com.example.recyclerviewdemo.databinding.ListItemLayoutCreateCourseBinding
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar

class CourseCreatorFragment : Fragment() {

    private var _binding: FragmentCourseCreatorBinding? = null
    private val binding get() = _binding!!
    val courseViewModel: CourseViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCourseCreatorBinding.inflate(inflater, container, false)
        val rootView = binding.root
        val numOfHoles = CourseCreatorFragmentArgs.fromBundle(requireArguments()).numOfHoles
        val holes: MutableList<Hole> = createHoleList(numOfHoles)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)


        //Recycler View
        val myAdapter = HoleAdapter(holes, courseViewModel)
        binding.recyclerView.adapter = myAdapter

        binding.SetCourseButton.setOnClickListener() {
            courseViewModel.updateCourse(binding.editTextTextPersonName2.text.toString(), binding.editTextTextPersonName3.text.toString())
            findNavController().navigateUp()

        }

        return rootView
    }
    fun createHoleList(numOfHoles: Int): MutableList<Hole> {
        val holes = mutableListOf<Hole>()

        for (i in 1..numOfHoles) {
            holes.add(Hole(0, 0))
        }

        return holes
    }
}

class HoleAdapter(val HoleList: List<Hole>, private val courseViewModel: CourseViewModel) : RecyclerView.Adapter<HoleViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoleViewHolder {
        val binding = ListItemLayoutCreateCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HoleViewHolder(binding, courseViewModel)
    }


    override fun onBindViewHolder(holder: HoleViewHolder, position: Int) {
        val currentHole = HoleList[position]
        holder.bindRound(currentHole, position+1, HoleList as MutableList<Hole>)
    }

    override fun getItemCount(): Int {
        return HoleList.size
    }

}


class HoleViewHolder(private val binding: ListItemLayoutCreateCourseBinding,
                     private val courseViewModel: CourseViewModel) :
    RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentHole: Hole
    init {
        binding.root.setOnClickListener { view ->
            courseViewModel.addHole(currentHole.par, currentHole.distance)
            val snackbar = Snackbar.make(binding.root, "Hole "+ binding.HoleNumber.text.toString()+" added", Snackbar.LENGTH_SHORT)
            snackbar.show()
        }
    }

    fun bindRound(Hole: Hole, holeNum : Int, holes: MutableList<Hole>) {
        currentHole = Hole
        binding.parThreeButton.isChecked = Hole.par == 3
        binding.parFourButton.isChecked = Hole.par == 4
        binding.parFiveButton.isChecked = Hole.par == 5
        binding.YardageEdittext.setText(Hole.distance.toString())
        binding.HoleNumber.text = holeNum.toString()

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            currentHole.par = when (checkedId) {
                binding.parThreeButton.id -> 3
                binding.parFourButton.id -> 4
                binding.parFiveButton.id -> 5
                else -> Hole.par
            }

        }

        binding.YardageEdittext.addTextChangedListener {
            currentHole.distance = it?.toString()?.toIntOrNull() ?: 0
        }

    }

}