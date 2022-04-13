package com.example.sportstats.teamsearch

import android.app.SearchManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportstats.R
import com.example.sportstats.databinding.FragmentTeamSearchBinding
import com.example.sportstats.network.SportsApi

const val SPORTS_STATS_PREF = "SportStatsPref"
const val TEAM_ID = "TeamId"
const val TEAM_NAME = "TeamName"

/**
 * A simple [Fragment] subclass.
 */
class TeamSearchFragment : Fragment() {

    // Initialize the ViewModel
    private val viewModel by viewModels<TeamSearchViewModel> {
        TeamSearchViewModelFactory(SportsApi.teamService)
    }

    private var _binding: FragmentTeamSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTeamSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Binding the ViewModel to view
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        // Getting the [RecyclerView] to set it up
        recyclerView = binding.teamsGrid
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Creating the adapter and attaching the lambda call to the onItemClick hook
        val teamAdapter = TeamSearchAdapter {
            // Navigate to the proper direction
            setPrefData(TEAM_ID, it.idTeam)
            setPrefData(TEAM_NAME, it.strTeam!!)
            view.findNavController()
                .navigate(TeamSearchFragmentDirections.actionTeamsFragmentToTeamStatsFragment())
        }
        binding.teamsGrid.adapter = teamAdapter
    }

    private fun setPrefData(key: String, value: String) {
        val sharedPref: SharedPreferences? = context?.getSharedPreferences(SPORTS_STATS_PREF, 0)
        val editor: SharedPreferences.Editor? = sharedPref?.edit()

        editor?.putString(key, value)
        editor?.apply()
    }

    /**
     * Create the options menu to manage the search bar
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu layout
        inflater.inflate(R.menu.options_menu, menu)
        // Getting the search item from the menu
        val searchItem: MenuItem = menu.findItem(R.id.search)!!
        // Getting the search manager from the system search service
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        // Getting the [SearchView]
        searchView = searchItem.actionView as SearchView
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))

        // Setting the listeners with the [TeamQueryTextListener] to manage the query search
        searchView?.setOnQueryTextListener(TeamQueryTextListener(viewModel))
        searchItem.expandActionView()

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}