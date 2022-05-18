package io.gubarsergey.auth

import io.gubarsergey.redux.redux.Reduce
import io.gubarsergey.redux.redux.Reducer
import io.gubarsergey.settings.Logout

data class AuthState(
    val email: String,
    val password: String,
    val token: Token,
    val userRole: UserRole,
    val name: String,
) {
    companion object {
        val default
            get() = AuthState(
                email = "serhii.hubar@nure.ua",
                password = "megubar123",
                token = Token(""),
                userRole = UserRole.CUSTOMER,
                name = "",
            )
    }

    data class Token(val value: String) {
        val bearerFormatted get() = "Bearer $value"
    }

    enum class UserRole {
        ARTIST,
        CUSTOMER
    }
}

val Reduce.authState by Reducer<AuthState> { state, action ->
    when (action) {
        is AuthEmailUpdated    -> state.copy(email = action.email)
        is AuthPasswordUpdated -> state.copy(password = action.password)
        is AuthSuccess         -> state.copy(
            token = AuthState.Token(action.token),
            userRole = when (action.role) {
                "artist" -> AuthState.UserRole.ARTIST
                else     -> AuthState.UserRole.CUSTOMER
            },
            name = action.name
        )
        is Logout              -> AuthState.default
        else                   -> state
    }
}
