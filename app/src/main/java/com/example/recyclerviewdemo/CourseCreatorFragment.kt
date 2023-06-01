package com.example.recyclerviewdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewdemo.databinding.FragmentCourseCreatorBinding
import com.example.recyclerviewdemo.databinding.FragmentCourseSelectorBinding
import com.example.recyclerviewdemo.databinding.ListItemLayoutCreateCourseBinding


class CourseCreatorFragment : Fragment() {

    private var _binding: FragmentCourseCreatorBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCourseCreatorBinding.inflate(inflater, container, false)
        val rootView = binding.root
        val holes : MutableList<Hole> = mutableListOf(Hole(0,0),Hole(0,0),Hole(0,0),Hole(0,0),Hole(0,0),Hole(0,0),Hole(0,0),Hole(0,0),Hole(0,0))



        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        //Spinner
        val holesArrayAdapter = activity?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.Holes,
                android.R.layout.simple_spinner_item)
        }
        holesArrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = holesArrayAdapter
        binding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            var numOfHoles = 0
            override fun onItemSelected(
                adapterView: AdapterView<*>,
                childView: View?,
                position: Int,
                itemId: Long
            ) {
                numOfHoles = adapterView.getItemAtPosition(position).toString().toInt()

            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {  }
        }

        //Recycler View
        val myAdapter = HoleAdapter(holes)
        binding.recyclerView.adapter = myAdapter

        binding.SetCourseButton.setOnClickListener() {
            val course = Course(binding.editTextTextPersonName2.text.toString(),holes,9,36)
            val result = Bundle().apply {
                // Put the result object in the bundle
                putParcelable("resultObjectKey", course)
            }
            findNavController().navigateUp()
            findNavController().previousBackStackEntry?.savedStateHandle?.set("resultKey", result)

        }

        return rootView
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

    fun bindRound(Hole: Hole, holeNum : Int, holes: MutableList<Hole>) {

        binding.HoleNumber.text = holeNum.toString()

        binding.parThreeButton.isChecked = Hole.par == 3
        binding.parFourButton.isChecked = Hole.par == 4
        binding.parFiveButton.isChecked = Hole.par == 5
        binding.YardageEdittext.setText(Hole.distance.toString())

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            Hole.par = when (checkedId) {
                binding.parThreeButton.id -> 3
                binding.parFourButton.id -> 4
                binding.parFiveButton.id -> 5
                else -> Hole.par
            }

        }

        binding.YardageEdittext.addTextChangedListener {
            Hole.distance = it?.toString()?.toIntOrNull() ?: 0
        }

    }

}