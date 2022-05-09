package io.gubarsergey.guards

import io.gubarsergey.ReduxAppState
import io.gubarsergey.redux.Middleware
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.redux.ReduxAction

data class OrderGuardReceived(
    val artistId: String,
) : GuardAction {
    override fun description(): String {
        return "No artist found for id '$artistId'"
    }
}

class OrderGuard(private val core: ReduxCore<ReduxAppState>) : Middleware {

    private val trackedCases: MutableList<String> = mutableListOf()

    override fun apply(action: ReduxAction) {
        if (core.state.createOrder.artistId != null && core.state.availableArtists.byId[core.state.createOrder.artistId] == null) {
            if (core.state.createOrder.artistId!! !in trackedCases) {
                trackedCases.add(core.state.createOrder.artistId!!)
                core.dispatch(OrderGuardReceived(core.state.createOrder.artistId!!))
            }
        }
    }
}
