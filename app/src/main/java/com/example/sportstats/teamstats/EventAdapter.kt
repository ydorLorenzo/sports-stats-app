package com.example.sportstats.teamstats

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sportstats.R
import com.example.sportstats.databinding.MatchResultItemBinding
import com.example.sportstats.network.EventResult

class EventAdapter : ListAdapter<EventResult, EventAdapter.EventViewHolder>(DiffCallback) {
    class EventViewHolder(private val binding: MatchResultItemBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(event: EventResult) {
            binding.event = event
            if (event.strStatus != "Not Started") {
                binding.eventResult.visibility = View.VISIBLE
                binding.eventResult.text = context.getString(R.string.match_score, event.intHomeScore, event.intAwayScore)
            } else {
                binding.eventResult.visibility = View.INVISIBLE
            }
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
            ), parent.context
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<EventResult>() {
            override fun areItemsTheSame(oldItem: EventResult, newItem: EventResult): Boolean {
                return oldItem.idEvent == newItem.idEvent
            }

            override fun areContentsTheSame(oldItem: EventResult, newItem: EventResult): Boolean {
                return oldItem == newItem
            }

        }
    }
}