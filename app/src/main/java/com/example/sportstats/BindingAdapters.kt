package com.example.sportstats

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.sportstats.network.EventResult
import com.example.sportstats.network.Team
import com.example.sportstats.teamsearch.ConnectionApiStatus
import com.example.sportstats.teamsearch.TeamSearchAdapter
import com.example.sportstats.teamstats.EventAdapter

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = "$imgUrl/preview".toUri().buildUpon().scheme("https").build()
        imageView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("listTeams")
fun bindSportRecyclerView(recyclerView: RecyclerView, data: List<Team>?) {
    val adapter = recyclerView.adapter as TeamSearchAdapter
    adapter.submitList(data)
}

@BindingAdapter("listLast5Events")
fun bindLast5RecyclerView(recyclerView: RecyclerView, data: List<EventResult>?) {
    val adapter = recyclerView.adapter as EventAdapter
    adapter.submitList(data)
}

@BindingAdapter("listNext5Events")
fun bindNext5RecyclerView(recyclerView: RecyclerView, data: List<EventResult>?) {
    val adapter = recyclerView.adapter as EventAdapter
    adapter.submitList(data)
}

@BindingAdapter("connectionApiStatus")
fun bindStatus(statusImageView: ImageView, status: ConnectionApiStatus) {
    when (status) {
        ConnectionApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ConnectionApiStatus.DONE -> statusImageView.visibility = View.GONE
        ConnectionApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
    }
}

@BindingAdapter("noDataInstantiated")
fun bindNoData(noDataTextView: TextView, noData: Boolean) {
    if (!noData) {
        noDataTextView.visibility = View.VISIBLE
    } else {
        noDataTextView.visibility = View.GONE
    }
}

@BindingAdapter("last5Text")
fun bindLast5EvenText(last5EventTextView: TextView, noData: Boolean) {
    if (noData) {
        last5EventTextView.visibility = View.VISIBLE
        last5EventTextView.setText(R.string.last_5)
    } else {
        last5EventTextView.visibility = View.GONE
    }
}

@BindingAdapter("next5Text")
fun bindNext5EvenText(next5EventTextView: TextView, noData: Boolean) {
    if (noData) {
        next5EventTextView.visibility = View.VISIBLE
        next5EventTextView.setText(R.string.next_5)
    } else {
        next5EventTextView.visibility = View.GONE
    }
}

@BindingAdapter("textWithoutNull")
fun bindCountryLeagueText(locationTextView: TextView, text: String) {
    if (text.contains("null ") || text.contains(" null")) {
        locationTextView.visibility = View.GONE
    } else {
        locationTextView.visibility = View.VISIBLE
        locationTextView.text = text
    }
}