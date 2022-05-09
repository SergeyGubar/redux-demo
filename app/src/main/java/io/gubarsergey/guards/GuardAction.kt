package io.gubarsergey.guards

import io.gubarsergey.redux.redux.ReduxAction

interface GuardAction : ReduxAction {
    fun description(): String
}
