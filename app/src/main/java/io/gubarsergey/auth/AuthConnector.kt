package io.gubarsergey.auth

import io.gubarsergey.BaseConnector
import io.gubarsergey.ReduxAppState
import io.gubarsergey.auth.ui.AuthProps
import io.gubarsergey.redux.ReduxCore

class AuthConnector(private val core: ReduxCore<ReduxAppState>) : BaseConnector<AuthProps>() {
    override fun map(appState: ReduxAppState): AuthProps {
        return AuthProps(
            email = appState.auth.email,
            password = appState.auth.password,
            login = core.bind(AuthPerformLogin),
            emailChanged = core.bindWith { AuthEmailUpdated(it) },
            passwordChanged = core.bindWith { AuthPasswordUpdated(it) }
        )
    }
}
