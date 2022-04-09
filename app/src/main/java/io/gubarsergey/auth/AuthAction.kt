package io.gubarsergey.auth

import io.gubarsergey.redux.redux.ReduxAction

data class AuthEmailUpdated(val email: String) : ReduxAction
data class AuthPasswordUpdated(val password: String) : ReduxAction

object AuthPerformLogin : ReduxAction
data class AuthSuccess(val token: String) : ReduxAction
object AuthFailed : ReduxAction
