package io.gubarsergey.auth

import io.gubarsergey.redux.redux.Reduce
import io.gubarsergey.redux.redux.Reducer

data class AuthState(
    val email: String,
    val password: String,
    val token: String,
) {
    companion object {
        val default get() = AuthState(email = "serhii.hubar@nure.ua", password = "megubar123", token = "")
    }
}

val Reduce.authState by Reducer<AuthState> { state, action ->
    when (action) {
        is AuthEmailUpdated    -> state.copy(email = action.email)
        is AuthPasswordUpdated -> state.copy(password = action.password)
        is AuthSuccess         -> state.copy(token = action.token)
        else                   -> state
    }
}
