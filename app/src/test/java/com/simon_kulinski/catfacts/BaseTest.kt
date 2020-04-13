package com.simon_kulinski.catfacts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.simon_kulinski.catfacts.data.CatFactsRepositoryImpl
import com.simon_kulinski.catfacts.data.network.CatFactsService
import com.simon_kulinski.catfacts.di.viewModelModule
import com.simon_kulinski.catfacts.domain.repositories.CatFactsRepository
import com.simon_kulinski.catfacts.domain.repositories.NetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
open class BaseTest : KoinTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        startKoin { modules(mockRepositoryModules, viewModelModule) }
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun clear() {
        stopKoin()
    }

    companion object {
        val mockWebServer = MockWebServer()
        val mockRepositoryModules = module {
            single<CatFactsRepository> {
                CatFactsRepositoryImpl(
                    CatFactsService.createCatFactService(mockWebServer.url("/")),
                    TestCoroutineDispatcher()
                )
            }

            single<NetworkManager> {
                Mockito.mock(NetworkManager::class.java)
            }

        }

        @AfterClass
        @JvmStatic
        fun shutDown() {
            mockWebServer.shutdown()
        }

    }


}