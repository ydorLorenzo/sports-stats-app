package com.example.sportstats.teamstats

import androidx.lifecycle.*
import com.example.sportstats.network.EventApiService
import com.example.sportstats.network.Team
import com.example.sportstats.network.TeamApiService
import com.example.sportstats.teamsearch.ConnectionApiStatus
import kotlinx.coroutines.launch

data class EventData(
    val id: String,
    val title: String,
    val leagueImg: String,
    val league: String,
    val date: String,
    val status: Boolean,
    val score: String?
)

class TeamStatsViewModel(
    private val teamService: TeamApiService,
    private val eventService: EventApiService
) : ViewModel() {

    // Connection status for the main call
    private val _status = MutableLiveData<ConnectionApiStatus>()
    val status: LiveData<ConnectionApiStatus> = _status

    // Actual Team to track
    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team> = _team

    // Last 5 matches of the selected Team
    private val _last5Matches = MutableLiveData<List<EventData>>()
    val last5Matches: LiveData<List<EventData>> = _last5Matches

    // Next 5 matches of the selected Team
    private val _next5Matches = MutableLiveData<List<EventData>>()
    val next5Matches: LiveData<List<EventData>> = _next5Matches

    fun getTeamStatsData(id: String) {
        viewModelScope.launch {
            _status.value = ConnectionApiStatus.LOADING
            try {
                _team.value = teamService.getTeamById(id).teams?.get(0)
                _last5Matches.value = eventService.getLastFiveEventsByTeamId(id).results
                    .map { event ->
                        EventData(
                            id = event.idEvent,
                            title = event.strEvent!!,
                            league = event.strLeague!!,
                            leagueImg = event.strThumb!!,
                            date = event.dateEvent!!,
                            score = if (event.intAwayScore != null && event.intHomeScore != null) "${event.intHomeScore} - ${event.intAwayScore}" else null,
                            status = !event.strStatus.equals("not started", true)
                        )
                    }
                _next5Matches.value = eventService.getNextFiveEventsByTeamId(id).events
                    .map { event ->
                        EventData(
                            id = event.idEvent,
                            title = event.strEvent!!,
                            league = event.strLeague!!,
                            leagueImg = event.strThumb!!,
                            date = event.dateEvent!!,
                            score = if (event.intAwayScore != null && event.intHomeScore != null) "${event.intHomeScore} - ${event.intAwayScore}" else null,
                            status = !event.strStatus.equals("not started", true)
                        )
                    }
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