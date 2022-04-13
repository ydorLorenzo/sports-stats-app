package com.example.sportstats.network

import retrofit2.http.GET
import retrofit2.http.Query

interface TeamApiService {
    // Call Search All Teams by League endpoint with query
    // parameter league -> 'l'
    @GET("search_all_teams.php")
    suspend fun getTeamByLeague(@Query("l") league: String): Teams

    // Call search teams by name with parameter team name 't'
    @GET("searchteams.php")
    suspend fun getTeamsByName(@Query("t") teamName: String): Teams

    // Call search team by id
    @GET("lookupteam.php")
    suspend fun getTeamById(@Query("id") teamId: String): Teams
}

interface EventApiService {
    // Look for last 5 events for a specific team giving teamId
    @GET("eventslast.php")
    suspend fun getLastFiveEventsByTeamId(@Query("id") teamId: String): EventLastResults

    // Look for next 5 events for a specific team giving teamId
    @GET("eventsnext.php")
    suspend fun getNextFiveEventsByTeamId(@Query("id") teamId: String): EventsNext
}