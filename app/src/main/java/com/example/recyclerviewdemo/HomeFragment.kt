package com.example.recyclerviewdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.androidfinalproject.RecyclerView.RoundAdapter
import com.example.recyclerviewdemo.databinding.FragmentHomeBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue


class MainFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseRef: DatabaseReference
    var round : MutableList<Round> = mutableListOf()
    private lateinit var myAdapter: RoundAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val rootView = binding.root
        databaseRef = FirebaseDatabase.getInstance().reference

        val ccsFalls: List<Hole> = listOf(
            Hole(520, 5), Hole(160, 3),
            Hole(350, 4), Hole(400, 4), Hole(480, 5),
            Hole(450, 4), Hole(420, 4), Hole(150, 3), Hole(450, 4)
        )

        val courses = listOf(
            Course("Country Club of Scranton (Falls)", ccsFalls, 9, 36, "Scranton, Pennsylvania", 3600),

            )
        val round = mutableListOf(
            Round(courses.get(0), listOf(5,4,4,4,5,4,3,3,5), calculateScoreToPar(listOf(5,4,4,4,5,4,3,3,5), courses.get(0))),
            Round(courses.get(0), listOf(5,3,4,4,5,4,3,3,5), calculateScoreToPar(listOf(5,3,4,4,5,4,3,3,5), courses.get(0))),
            Round(courses.get(0), listOf(5,2,4,4,5,4,3,3,5), calculateScoreToPar(listOf(5,2,4,4,5,4,3,3,5), courses.get(0)))
        )

        myAdapter = RoundAdapter(round)
        binding.recyclerView.adapter = myAdapter

        binding.addRoundButton.setOnClickListener{view ->
            val action = MainFragmentDirections.actionMainFragmentToCourseSelectorFragment()
            rootView.findNavController().navigate(action)
        }
        databaseRef.child("rounds").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val allDBEntries = snapshot.children

                var numOfEntriesAdded = 0
                for (singleEntryEntry in allDBEntries) {
                    numOfEntriesAdded++

                    val holeList: MutableList<Hole> = mutableListOf()
                    val holeSnapshot = singleEntryEntry.child("course").child("holes")

                    for (holeSnapshotChild in holeSnapshot.children) {
                        val hole = holeSnapshotChild.getValue(Hole::class.java)
                        hole?.let {
                            holeList.add(it)
                        }
                    }
                    val scoreToPar = singleEntryEntry.child("scoreToPar").getValue().toString().toInt()
                    val course = Course(
                        singleEntryEntry.child("course").child("place").getValue(String::class.java)!!,
                        holeList,
                        singleEntryEntry.child("course").child("numOfHoles").getValue(Int::class.java)!!,
                        singleEntryEntry.child("course").child("totalPar").getValue(Int::class.java)!!,
                        singleEntryEntry.child("course").child("location").getValue(String::class.java)!!,
                        singleEntryEntry.child("course").child("totalDistance").getValue(Int::class.java)!!
                    )

                    val date = singleEntryEntry.child("scorecard").getValue<List<Int>>()
                    round.add(Round(course, date, scoreToPar))

                }
                myAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return rootView
    }

    fun calculateScoreToPar( score : List<Int>, course : Course) : Int{
        var totalScore = 0
        for(each in score)
            totalScore+=each
        return totalScore-course.totalPar
    }
}

