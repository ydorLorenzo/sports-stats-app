package com.example.sportstats.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SportsApiTest : BaseTest() {
    private lateinit var serviceTeam: TeamApiService
    private lateinit var serviceEvent: EventApiService

    @Before
    fun setup() {
        val url = mockWebServer.url("/")

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .build()
        serviceTeam = retrofit.create(TeamApiService::class.java)
        serviceEvent = retrofit.create(EventApiService::class.java)
    }

    @Test
    fun api_team_service() {
        enqueue("teams.json")
        runBlocking {
            val apiResponse = serviceTeam.getTeamByLeague("4328")
            TestCase.assertNotNull(apiResponse)
            TestCase.assertTrue("The list has sports", apiResponse.teams!!.isNotEmpty())
            TestCase.assertEquals("The IDs did not match", "134778", apiResponse.teams!![0].idTeam)
        }
    }

    @Test
    fun api_last_events_service() {
        enqueue("last_events.json")
        runBlocking {
            val apiResponse = serviceEvent.getLastFiveEventsByTeamId("144455")
            TestCase.assertNotNull(apiResponse)
            TestCase.assertTrue("The list has sports", apiResponse.results.isNotEmpty())
            TestCase.assertEquals(
                "The IDs did not match",
                "1545107",
                apiResponse.results[0].idEvent
            )
        }
    }

    @Test
    fun api_next_events_service() {
        enqueue("next_events.json")
        runBlocking {
            val apiResponse = serviceEvent.getNextFiveEventsByTeamId("133739")
            TestCase.assertNotNull(apiResponse)
            TestCase.assertTrue("The list has sports", apiResponse.events.isNotEmpty())
            TestCase.assertEquals("The IDs did not match", "1537171", apiResponse.events[0].idEvent)
        }
    }
}