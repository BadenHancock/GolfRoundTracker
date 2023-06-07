package com.example.androidfinalproject.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewdemo.Round
import com.example.recyclerviewdemo.databinding.ListItemLayoutRoundBinding

class RoundAdapter(val RoundList: List<Round>) : RecyclerView.Adapter<RoundViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundViewHolder {
        val binding = ListItemLayoutRoundBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoundViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RoundViewHolder, position: Int) {
        val currentRound = RoundList[position]
        holder.bindRound(currentRound)
    }

    override fun getItemCount(): Int {
        return RoundList.size
    }
}