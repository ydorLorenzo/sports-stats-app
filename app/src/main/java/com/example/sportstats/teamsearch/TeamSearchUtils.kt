package com.example.sportstats.teamsearch

import androidx.appcompat.widget.SearchView

class TeamQueryTextListener(private val searchTeamByName: (name: String) -> Unit) :
    SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(p0: String?): Boolean {
        if (p0 == null) return false

        searchTeamByName(p0)
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        if (p0 == null) return false
        return true
    }

}