package io.gubarsergey.auth

import io.gubarsergey.ReduxAppState
import io.gubarsergey.auth.service.AuthAPI
import io.gubarsergey.auth.service.AuthRequestDto
import io.gubarsergey.redux.Middleware
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.redux.ReduxAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.inject
import timber.log.Timber

class AuthMiddleware(
    private val core: ReduxCore<ReduxAppState>
) : Middleware {

    private val authApi: AuthAPI by inject()

    override fun apply(action: ReduxAction) {
        val state = core.store.appState()
        when (action) {
            is AuthPerformLogin -> {
                launch {
                    withContext(Dispatchers.IO) {
                        val result = runCatching {
                            authApi.login(AuthRequestDto(state.auth.email, state.auth.password))
                        }
                        result.fold(
                            onSuccess = { core.dispatch(AuthSuccess(it.access_token)) },
                            onFailure = { core.dispatch(AuthFailed) }
                        )
                        Timber.d("Result $result")
                    }
                }
            }
        }
    }
}
