package com.example.recyclerviewdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewdemo.databinding.FragmentCreateRoundBinding
import com.example.recyclerviewdemo.databinding.ListItemLayoutCreateRoundBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateRoundFragment : Fragment() {

    private var _binding: FragmentCreateRoundBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseRef: DatabaseReference
    private val viewModel: CreateRoundViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databaseRef = FirebaseDatabase.getInstance().reference
        _binding = FragmentCreateRoundBinding.inflate(inflater, container, false)
        val rootView = binding.root
        binding.holesRecyclerView.layoutManager = LinearLayoutManager(activity)
        val course : Course = CreateRoundFragmentArgs.fromBundle(requireArguments()).Course
        binding.courseNameTextView.text =  course.place

        val myAdapter = createRoundAdapter(course.holes, viewModel)
        binding.holesRecyclerView.adapter = myAdapter
        binding.SetRoundButton.setOnClickListener() {
            val round = Round(course, viewModel.scores, calculateScoreToPar(course))
            uploadRoundToFirebase(round)
            viewModel.clearScores()
        }
        return rootView
    }


    fun calculateScoreToPar(course : Course) : Int{
        var score = 0
        var currNum = 0
        for(each in viewModel.scores) {
            score +=  (course.holes?.get(currNum)?.par ?: 0) - each
            currNum++
        }
        return score
    }












    private fun uploadRoundToFirebase(round: Round) {
        val roundRef = databaseRef.child("rounds").push()
        roundRef.setValue(round)
            .addOnSuccessListener {
                Snackbar.make(binding.root, "Round uploaded to Firebase", Snackbar.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Snackbar.make(binding.root, "Failed to upload round: ${exception.message}", Snackbar.LENGTH_SHORT).show()
            }
    }

}








class createRoundAdapter(val HoleList: List<Hole>?, private val courseViewModel: CreateRoundViewModel) : RecyclerView.Adapter<CreateRoundViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateRoundViewHolder {
        val binding = ListItemLayoutCreateRoundBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CreateRoundViewHolder(binding, courseViewModel)
    }


    override fun onBindViewHolder(holder: CreateRoundViewHolder, position: Int) {
        val currentHole = HoleList?.get(position)
        holder.bindRound(currentHole, position+1, HoleList as MutableList<Hole>)
    }

    override fun getItemCount(): Int {
        return HoleList?.size ?: 0
    }

}


class CreateRoundViewHolder(private val binding: ListItemLayoutCreateRoundBinding,
                     private val viewModel: CreateRoundViewModel) :
    RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentHole: Hole
    init {
        binding.root.setOnClickListener { view ->
            viewModel.addScore(binding.scoreEdittext.text.toString().toInt())
            val snackbar = Snackbar.make(binding.root, "Hole "+ binding.HoleNumber.text.toString()+" score added", Snackbar.LENGTH_SHORT)
            snackbar.show()
        }
    }

    fun bindRound(Hole: Hole?, holeNum : Int, holes: MutableList<Hole>) {
        if (Hole != null) {
            currentHole = Hole
        }
        binding.HoleNumber.text = holeNum.toString()
        binding.ParTextView.text = "Par: " + currentHole.par.toString()
        binding.DistanceTextView.text = "Distance: " + currentHole.distance.toString()
        binding.scoreTextView.text = "Score: "


        binding.scoreEdittext.addTextChangedListener {
            currentHole.distance = it?.toString()?.toIntOrNull() ?: 0
        }

    }

}
