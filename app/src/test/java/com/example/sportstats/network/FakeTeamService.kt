package com.example.sportstats.network

class FakeTeamService(var teams: MutableList<Team>? = mutableListOf()) : TeamApiService,
    BaseTest() {

    override suspend fun getTeamByLeague(league: String): Teams {
        return Teams(teams?.filter { it.idLeague == league })
    }

    override suspend fun getTeamsByName(teamName: String): Teams {
        return Teams(teams?.filter { it.strTeam == teamName })
    }

    override suspend fun getTeamById(teamId: String): Teams {
        return Teams(teams?.filter { it.idTeam == teamId })
    }
}