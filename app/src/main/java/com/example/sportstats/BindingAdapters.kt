package com.example.sportstats

import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.sportstats.teamsearch.ConnectionApiStatus
import com.example.sportstats.teamsearch.TeamItemUiEvent
import com.example.sportstats.teamsearch.TeamSearchAdapter
import com.example.sportstats.teamstats.EventAdapter
import com.example.sportstats.teamstats.EventData

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
fun bindSportRecyclerView(recyclerView: RecyclerView, data: List<TeamItemUiEvent>?) {
    val adapter = recyclerView.adapter as TeamSearchAdapter
    adapter.submitList(data)
}

@BindingAdapter("listLast5Events")
fun bindLast5RecyclerView(recyclerView: RecyclerView, data: List<EventData>?) {
    val adapter = recyclerView.adapter as EventAdapter
    adapter.submitList(data)
}

@BindingAdapter("listNext5Events")
fun bindNext5RecyclerView(recyclerView: RecyclerView, data: List<EventData>?) {
    val adapter = recyclerView.adapter as EventAdapter
    adapter.submitList(data)
}

@BindingAdapter("connectionApiStatus")
fun bindStatus(statusImageView: ImageView, status: ConnectionApiStatus?) {
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
        else -> statusImageView.visibility = View.GONE
    }
}

@BindingAdapter("connectionApiStatus")
fun bindStatusTextView(statusImageView: TextView, status: ConnectionApiStatus?) {
    statusImageView.visibility = when (status) {
        ConnectionApiStatus.DONE -> View.GONE
        else -> View.VISIBLE
    }
}

@BindingAdapter("noDataInstantiated")
fun bindNoDataTextView(noDataTextView: TextView, noData: Boolean?) {
    noDataTextView.visibility = if (noData == null || noData) View.VISIBLE else View.GONE
}

@BindingAdapter("reverseNoDataInstantiated")
fun bindReverseNoDataTextView(reverseNoDataTextView: TextView, noData: Boolean?) {
    reverseNoDataTextView.visibility = if (noData == null || noData) View.GONE else View.VISIBLE
}

@BindingAdapter("noDataInstantiated")
fun bindNoDataScrollView(noDataScrollView: ScrollView, noData: Boolean?) {
    noDataScrollView.visibility = if (noData == null || noData) View.VISIBLE else View.GONE
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