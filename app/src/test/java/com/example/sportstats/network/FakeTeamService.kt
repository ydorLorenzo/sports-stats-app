package com.example.sportstats.network

class FakeTeamService : TeamApiService {
    private var teamServiceData = Teams(listOf())

    override suspend fun getTeamByLeague(league: String): Teams {
        return teamServiceData.copy(teamServiceData.teams?.filter { it.strLeague == league })
    }

    override suspend fun getTeamsByName(teamName: String): Teams {
        return teamServiceData.copy(teamServiceData.teams?.filter { it.strTeam == teamName })
    }

    override suspend fun getTeamById(teamId: String): Teams {
        return teamServiceData.copy(teamServiceData.teams?.filter { it.idTeam == teamId })
    }

    fun addTeam(vararg teams: Team) {
        val newTeams =
            if (teamServiceData.teams == null) mutableListOf<Team>()
            else teamServiceData.teams!!.toMutableList()
        newTeams.addAll(teams)
        teamServiceData = Teams(newTeams)
    }

    fun clearData() {
        teamServiceData = Teams(listOf())
    }
}