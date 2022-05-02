package io.gubarsergey.auth

import io.gubarsergey.redux.redux.Reduce
import io.gubarsergey.redux.redux.Reducer

data class AuthState(
    val email: String,
    val password: String,
    val token: Token,
) {
    companion object {
        val default get() = AuthState(email = "serhii.hubar@nure.ua", password = "megubar123", token = Token(""))
    }

    data class Token(val value: String) {
        val bearerFormatted get() = "Bearer $value"
    }
}

val Reduce.authState by Reducer<AuthState> { state, action ->
    when (action) {
        is AuthEmailUpdated    -> state.copy(email = action.email)
        is AuthPasswordUpdated -> state.copy(password = action.password)
        is AuthSuccess         -> state.copy(token = AuthState.Token(action.token))
        else                   -> state
    }
}
