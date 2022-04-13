package com.example.sportstats.teamstats

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportstats.R
import com.example.sportstats.databinding.FragmentTeamStatsBinding
import com.example.sportstats.network.SportsApi
import com.example.sportstats.teamsearch.SPORTS_STATS_PREF
import com.example.sportstats.teamsearch.TEAM_ID
import com.example.sportstats.teamsearch.TEAM_NAME


/**
 * A simple [Fragment] subclass.
 */
class TeamStatsFragment : Fragment() {

    // Initialize the ViewModel
    private val viewModel by viewModels<TeamStatsViewModel> {
        TeamStatsViewModelFactory(SportsApi.teamService, SportsApi.eventService)
    }

    private lateinit var teamId: String
    private lateinit var teamName: String
    private lateinit var last5RecyclerView: RecyclerView
    private lateinit var next5RecyclerView: RecyclerView
    private var _binding: FragmentTeamStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        teamId = getPrefData(TEAM_ID).toString()
        teamName = getPrefData(TEAM_NAME).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTeamStatsBinding.inflate(inflater, container, false)
        viewModel.getTeamStatsData(teamId)

        binding.viewModel = viewModel
        binding.isTeamSaved = teamId != "null"
        binding.lifecycleOwner = this
        last5RecyclerView = binding.last5Recycler
        next5RecyclerView = binding.next5Recycler
        last5RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        next5RecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val eventLastAdapter = EventAdapter()
        val eventNextAdapter = EventAdapter()
        binding.last5Recycler.adapter = eventLastAdapter
        binding.next5Recycler.adapter = eventNextAdapter
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.team_stats_menu, menu)
    }

    private fun getPrefData(key: String): String? {
        val sharedPref: SharedPreferences? = context?.getSharedPreferences(SPORTS_STATS_PREF, 0)
        return sharedPref?.getString(key, null)
    }

    private fun removePrefData(key: String) {
        val sharedPref: SharedPreferences? = context?.getSharedPreferences(SPORTS_STATS_PREF, 0)
        val editor: SharedPreferences.Editor? = sharedPref?.edit()

        editor?.remove(key)
        editor?.apply()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cancel -> {
                val action = TeamStatsFragmentDirections.actionTeamStatsFragmentToTeamsFragment()
                removePrefData(TEAM_ID)
                removePrefData(TEAM_NAME)
                activity?.findNavController(R.id.team_stats_fragment)?.navigate(action)
                true
            }
            else -> false
        }
    }
}