package com.example.sportstats.teamsearch

import androidx.lifecycle.*
import com.example.sportstats.network.Team
import com.example.sportstats.network.TeamApiService
import kotlinx.coroutines.launch

enum class ConnectionApiStatus { LOADING, ERROR, DONE }

data class TeamItemUiEvent(
    val teamId: String,
    val teamName: String,
    val teamImg: String,
    val sportName: String,
    val mainLeague: String,
    val onTeamSelect: () -> Unit
)

class TeamSearchViewModel(private val teamService: TeamApiService) : ViewModel() {

    private val _status = MutableLiveData<ConnectionApiStatus>()
    val status: LiveData<ConnectionApiStatus> = _status

    private val _teamsSearched = MutableLiveData<List<TeamItemUiEvent>>()
    val teamsSearched: LiveData<List<TeamItemUiEvent>> = _teamsSearched

    init {
        onSearchTeamByName()
    }

    fun onSearchTeamByName(name: String = "") {
        viewModelScope.launch {
            _status.value = ConnectionApiStatus.LOADING
            try {
                _teamsSearched.value = teamService.getTeamsByName(name).teams
                    ?.filter { it.strLocked == "unlocked" && it.idLeague != null }
                    ?.map { team ->
                        TeamItemUiEvent(
                            teamId = team.idTeam,
                            teamName = team.strTeam!!,
                            teamImg = team.strTeamBadgeUrl!!,
                            sportName = team.strSport!!,
                            mainLeague = team.strLeague!!,
                            onTeamSelect = {}
                        )
                    }
                _status.value = ConnectionApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ConnectionApiStatus.ERROR
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