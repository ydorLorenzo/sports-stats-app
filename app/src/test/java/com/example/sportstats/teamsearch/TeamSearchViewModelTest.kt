package com.example.sportstats.teamsearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.sportstats.ServiceLocator
import com.example.sportstats.network.BaseTest
import com.example.sportstats.network.FakeTeamService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TeamSearchViewModelTest : BaseTest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Subject under test
    private lateinit var teamSearchViewModel: TeamSearchViewModel

    private lateinit var serviceTeam: FakeTeamService

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        serviceTeam = FakeTeamService()
        ServiceLocator.teamService = serviceTeam
        teamSearchViewModel = TeamSearchViewModel(serviceTeam)
    }

    @After
    fun cleanup() {
        ServiceLocator.resetService()
    }

    @Test
    fun searchTeamByName_loading() = runTest {
        // When
        val job = launch {
            teamSearchViewModel.searchTeamByName()
        }
        // Then
        assertThat(teamSearchViewModel.status.value, `is`(nullValue()))
        // When
        job.join()
        // Then
        assertThat(teamSearchViewModel.status.value, `is`(ConnectionApiStatus.DONE))
    }
}