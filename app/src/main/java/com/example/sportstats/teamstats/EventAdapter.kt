package com.example.sportstats.teamstats

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sportstats.databinding.MatchResultItemBinding

class EventAdapter : ListAdapter<EventData, EventAdapter.EventViewHolder>(DiffCallback) {
    class EventViewHolder(
        private val binding: MatchResultItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(event: EventData) {
            binding.event = event
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventViewHolder {
        return EventViewHolder(
            MatchResultItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<EventData>() {
            override fun areItemsTheSame(oldItem: EventData, newItem: EventData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: EventData, newItem: EventData): Boolean {
                return oldItem == newItem
            }

        }
    }
}