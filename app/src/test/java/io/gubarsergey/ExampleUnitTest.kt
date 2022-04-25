package io.gubarsergey

import io.gubarsergey.auth.AuthEmailUpdated
import io.gubarsergey.auth.AuthMiddleware
import io.gubarsergey.auth.AuthPerformLogin
import io.gubarsergey.auth.authState
import io.gubarsergey.auth.service.AuthAPI
import io.gubarsergey.auth.service.AuthResponseDto
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.redux.Reduce
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.system.measureTimeMillis

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest : KoinTest {

    private val authApi: AuthAPI = mockk()

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `redux login`() = runTest {

        startKoin {
            modules(
                module {
                    single { authApi }
                }
            )
        }

        val core = ReduxCore(
            defaultState = ReduxAppState.default,
            applyReduce = { state, action ->
                state.copy(
                    auth = Reduce.authState(state.auth, action)
                )
            },
            dispatchOnUiThread = { it() }
        )
        core.withMiddlewares(listOf(AuthMiddleware(core)))
        coEvery { authApi.login(any()) } returns AuthResponseDto(access_token = "token")

        val result = measureTimeMillis {
            core.dispatch(AuthEmailUpdated("testemail"))
            core.dispatch(AuthPerformLogin)
            expectThat(core.state.auth.email).isEqualTo("testemail")
        }

        println(result)

    }
}
