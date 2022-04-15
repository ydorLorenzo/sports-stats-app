package com.example.sportstats.teamstats

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportstats.R
import com.example.sportstats.ServiceLocator
import com.example.sportstats.databinding.FragmentTeamStatsBinding
import com.example.sportstats.teamsearch.ConnectionApiStatus
import com.example.sportstats.teamsearch.SPORTS_STATS_PREF
import com.example.sportstats.teamsearch.TEAM_ID
import com.example.sportstats.teamsearch.TEAM_NAME


/**
 * App main fragment.
 */
class TeamStatsFragment : Fragment() {

    // Initialize the ViewModel
    private val viewModel by viewModels<TeamStatsViewModel> {
        TeamStatsViewModelFactory(
            ServiceLocator.provideTeamService(),
            ServiceLocator.provideEventService()
        )
    }

    // Sport Team reference in SharedPreferences
    private var teamId: String? = null
    private var teamName: String? = null

    // RecyclerView reference
    private lateinit var last5RecyclerView: RecyclerView
    private lateinit var next5RecyclerView: RecyclerView

    // Layout binding
    private var _binding: FragmentTeamStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        teamId = getPreference(TEAM_ID)
        teamName = getPreference(TEAM_NAME)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTeamStatsBinding.inflate(inflater, container, false)
        if (teamId is String) viewModel.getTeamStatsData(teamId!!)

        binding.viewModel = viewModel
        binding.isTeamSaved = teamId != null
        binding.lifecycleOwner = this
        last5RecyclerView = binding.last5Recycler
        next5RecyclerView = binding.next5Recycler
        last5RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        next5RecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.last5Recycler.adapter = EventAdapter()
        binding.next5Recycler.adapter = EventAdapter()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.team_stats_menu, menu)
    }

    /**
     * Get the [key] [SharedPreferences]
     */
    private fun getPreference(key: String): String? {
        val sharedPref: SharedPreferences = requireContext()
            .getSharedPreferences(SPORTS_STATS_PREF, Context.MODE_PRIVATE)
        return sharedPref.getString(key, null)
    }

    /**
     * Clear the [SharedPreferences]
     */
    private fun clearPreferences(): Boolean {
        val sharedPref: SharedPreferences = requireContext()
            .getSharedPreferences(SPORTS_STATS_PREF, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.clear()
        return editor.commit()
    }

    /**
     * Navigate to TeamSearchFragment and clear the preferences
     * references to previous selected Team
     */
    private fun onSearchNewTeam() {
        val action = TeamStatsFragmentDirections.actionTeamStatsFragmentToTeamsFragment()
        findNavController().navigate(action)
        clearPreferences()
    }

    /**
     * Handles the menu interaction
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle search button onClick event
        return if (item.itemId == R.id.cancel) {
            onSearchNewTeam()
            true
        } else false
    }
}