package com.example.sportstats.teamsearch

import androidx.lifecycle.*
import com.example.sportstats.network.Team
import com.example.sportstats.network.TeamApiService
import kotlinx.coroutines.launch

enum class ConnectionApiStatus { LOADING, ERROR, DONE }

class TeamSearchViewModel(private val teamService: TeamApiService) : ViewModel() {

    private val _status = MutableLiveData<ConnectionApiStatus>()
    val status: LiveData<ConnectionApiStatus> = _status

    private val _teams = MutableLiveData<List<Team>>()
    val teams: LiveData<List<Team>> = _teams

    init {
        searchTeamByName("real")
    }

    fun searchTeamByName(name: String = "") {
        viewModelScope.launch {
            _status.value = ConnectionApiStatus.LOADING
            try {
                val resultTeams = teamService.getTeamsByName(name)
                _teams.value =
                    resultTeams.teams?.filter { it.strLocked == "unlocked" && it.idLeague != null }
                        ?: listOf()
                _status.value = ConnectionApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ConnectionApiStatus.ERROR
                _teams.value = listOf()
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class TeamSearchViewModelFactory(private val teamService: TeamApiService) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        (TeamSearchViewModel(teamService) as T)
}