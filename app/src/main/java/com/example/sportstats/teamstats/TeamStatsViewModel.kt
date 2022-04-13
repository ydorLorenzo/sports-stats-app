package com.example.sportstats.teamstats

import androidx.lifecycle.*
import com.example.sportstats.network.EventApiService
import com.example.sportstats.network.EventResult
import com.example.sportstats.network.Team
import com.example.sportstats.network.TeamApiService
import com.example.sportstats.teamsearch.ConnectionApiStatus
import kotlinx.coroutines.launch

class TeamStatsViewModel(
    private val teamService: TeamApiService,
    private val eventService: EventApiService
) : ViewModel() {
    private val _status = MutableLiveData<ConnectionApiStatus>()
    val status: LiveData<ConnectionApiStatus> = _status
    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team> = _team
    private val _last5Matches = MutableLiveData<List<EventResult>>()
    val last5Matches: LiveData<List<EventResult>> = _last5Matches
    private val _next5Matches = MutableLiveData<List<EventResult>>()
    val next5Matches: LiveData<List<EventResult>> = _next5Matches

    fun getTeamStatsData(id: String) {
        viewModelScope.launch {
            _status.value = ConnectionApiStatus.LOADING
            try {
                val resultTeam = teamService.getTeamById(id)
                val resultLast5 = eventService.getLastFiveEventsByTeamId(id)
                val resultNext5 = eventService.getNextFiveEventsByTeamId(id)
                _team.value = if (resultTeam.teams != null) resultTeam.teams[0] else null
                _next5Matches.value = resultNext5.events
                _last5Matches.value = resultLast5.results
                _status.value = ConnectionApiStatus.DONE
            } catch (e: Exception) {
                _status.value =
                    if (id != "null") ConnectionApiStatus.ERROR
                    else ConnectionApiStatus.DONE
            }
        }

    }
}

@Suppress("UNCHECKED_CAST")
class TeamStatsViewModelFactory(
    private val teamService: TeamApiService,
    private val eventService: EventApiService
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        (TeamStatsViewModel(teamService, eventService) as T)
}