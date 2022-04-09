package io.gubarsergey

import io.gubarsergey.auth.AuthState

data class ReduxAppState(
    val auth: AuthState,
)
