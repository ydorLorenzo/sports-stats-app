package com.example.sportstats.teamsearch

import androidx.appcompat.widget.SearchView

class TeamQueryTextListener(private val viewModel: TeamSearchViewModel) :
    SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(p0: String?): Boolean {
        if (p0 == null) return false

        viewModel.searchTeamByName(p0)
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        if (p0 == null) return false
        return true
    }

}