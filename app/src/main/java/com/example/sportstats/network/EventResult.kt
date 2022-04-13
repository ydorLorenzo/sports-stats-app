package com.example.sportstats.network

import com.squareup.moshi.Json

data class EventResult(
    val idEvent: String,
    @Json(name = "idSoccerXML") val idSoccerXml: String? = null,
    @Json(name = "idAPIfootball") val idApiFootball: String? = null,
    val strEvent: String? = null,
    val strEventAlternate: String? = null,
    val strFilename: String? = null,
    val strSport: String? = null,
    val idLeague: String? = null,
    val strLeague: String? = null,
    val strSeason: String? = null,
    @Json(name = "strDescriptionEN") val strDescriptionEn: String? = null,
    val strHomeTeam: String? = null,
    val strAwayTeam: String? = null,
    val intHomeScore: String? = null,
    val intRound: String? = null,
    val intAwayScore: String? = null,
    val intSpectators: String? = null,
    val strOfficial: String? = null,
    val strTimestamp: String? = null,
    val dateEvent: String? = null,
    val dateEventLocal: String? = null,
    val strTime: String? = null,
    val strTimeLocal: String? = null,
    @Json(name = "strTVStation") val strTvStation: String? = null,
    val idHomeTeam: String? = null,
    val idAwayTeam: String? = null,
    val intScore: String? = null,
    val intScoreVotes: String? = null,
    val strResult: String? = null,
    val strVenue: String? = null,
    val strCountry: String? = null,
    val strCity: String? = null,
    val strPoster: String? = null,
    val strSquare: String? = null,
    @Json(name = "strFanart") val strFanArt: String? = null,
    val strThumb: String? = null,
    val strBanner: String? = null,
    val strMap: String? = null,
    val strTweet1: String? = null,
    val strTweet2: String? = null,
    val strTweet3: String? = null,
    val strVideo: String? = null,
    val strStatus: String? = null,
    val strPostponed: String? = null,
    val strLocked: String? = null
)

data class EventLastResults(
    val results: List<EventResult>
)

data class EventsNext(
    val events: List<EventResult>
)