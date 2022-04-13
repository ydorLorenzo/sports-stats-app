package com.example.sportstats.network

class FakeEventService(var events: MutableList<EventResult>? = mutableListOf()) :
    EventApiService {
    override suspend fun getLastFiveEventsByTeamId(teamId: String): EventLastResults {
        return EventLastResults(events
            ?.filter { it.idHomeTeam == teamId }
            ?.sortedBy { it.dateEvent }
            ?.slice(0..5)!!
        )
    }

    override suspend fun getNextFiveEventsByTeamId(teamId: String): EventsNext {
        return EventsNext(events
            ?.filter { it.idHomeTeam == teamId }
            ?.sortedBy { it.dateEvent }
            ?.reversed()
            ?.slice(0..5)!!
        )
    }
}