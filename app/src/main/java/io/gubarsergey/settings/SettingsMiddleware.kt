package io.gubarsergey.settings

import io.gubarsergey.Router
import io.gubarsergey.redux.Middleware
import io.gubarsergey.redux.redux.ReduxAction

class SettingsMiddleware : Middleware {
    override fun apply(action: ReduxAction) {
        when (action) {
            is Logout -> {
                Router.goToLogin()
            }
        }
    }
}
