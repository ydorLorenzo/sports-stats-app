package com.example.sportstats.network

class FakeEventService : EventApiService {
    private var eventServiceData = EventLastResults(listOf())

    override suspend fun getLastFiveEventsByTeamId(teamId: String): EventLastResults {
        return EventLastResults(
            eventServiceData.results.filter {
                it.idHomeTeam == teamId || it.idAwayTeam == teamId
            })
    }

    override suspend fun getNextFiveEventsByTeamId(teamId: String): EventsNext {
        return EventsNext(eventServiceData.results.filter {
            it.idAwayTeam == teamId || it.idHomeTeam == teamId
        })
    }

    fun addEvent(vararg events: EventResult) {
        val results = eventServiceData.results.toMutableList()
        results.addAll(events)
        eventServiceData = EventLastResults(results)
    }

    fun clearData() {
        eventServiceData = EventLastResults(listOf())
    }
}