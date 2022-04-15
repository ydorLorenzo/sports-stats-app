package com.example.sportstats.teamsearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sportstats.databinding.CardViewTeamItemBinding
import com.example.sportstats.network.Team

class TeamSearchAdapter(private val onItemClick: (TeamItemUiEvent) -> Unit) :
    ListAdapter<TeamItemUiEvent, TeamSearchAdapter.TeamViewHolder>(DiffCallback) {

    class TeamViewHolder(private val binding: CardViewTeamItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(team: TeamItemUiEvent) {
            binding.team = team
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val viewHolder = TeamViewHolder(
            CardViewTeamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClick(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<TeamItemUiEvent>() {

            override fun areItemsTheSame(oldItem: TeamItemUiEvent, newItem: TeamItemUiEvent): Boolean {
                return oldItem.teamId == newItem.teamId
            }

            override fun areContentsTheSame(oldItem: TeamItemUiEvent, newItem: TeamItemUiEvent): Boolean {
                return oldItem == newItem
            }
        }
    }
}