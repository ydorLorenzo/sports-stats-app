package com.example.sportstats

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.example.sportstats.network.EventApiService
import com.example.sportstats.network.TeamApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val API_KEY = "50130162"
private const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/$API_KEY/"

object ServiceLocator {

    private var retrofitService: Retrofit? = null

    @Volatile
    var teamService: TeamApiService? = null
        @VisibleForTesting set

    @Volatile
    var eventService: EventApiService? = null
        @VisibleForTesting set

    private var apiBaseUrl: String = BASE_URL

    private val lock = Any()

    @VisibleForTesting
    fun resetService() {
        synchronized(lock) {
            retrofitService = null
            teamService = null
            eventService = null
        }
    }

    fun provideTeamService(baseUrl: HttpUrl? = null): TeamApiService {
        synchronized(this) {
            return teamService ?: createTeamService(baseUrl ?: apiBaseUrl.toHttpUrl())
        }
    }

    fun provideEventService(baseUrl: HttpUrl? = null): EventApiService {
        synchronized(this) {
            return eventService ?: createEventService(baseUrl ?: apiBaseUrl.toHttpUrl())
        }
    }

    private fun createEventService(baseUrl: HttpUrl): EventApiService {
        val newRetrofit = retrofitService ?: createRetrofitService(baseUrl)
        val newEventService: EventApiService by lazy {
            newRetrofit.create(EventApiService::class.java)
        }
        eventService = newEventService
        return newEventService
    }

    private fun createTeamService(baseUrl: HttpUrl): TeamApiService {
        val newRetrofit = retrofitService ?: createRetrofitService(baseUrl)
        val newTeamService: TeamApiService by lazy {
            newRetrofit.create(TeamApiService::class.java)
        }
        teamService = newTeamService
        return newTeamService
    }

    private fun createRetrofitService(url: HttpUrl): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val newRetrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(url)
            .build()
        retrofitService = newRetrofit
        return newRetrofit
    }

}