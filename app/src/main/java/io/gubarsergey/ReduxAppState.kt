package io.gubarsergey

import io.gubarsergey.artists.AvailableArtistsState
import io.gubarsergey.auth.AuthState
import io.gubarsergey.orders.OrdersState

data class ReduxAppState(
    val auth: AuthState,
    val myOrders: OrdersState,
    val availableArtists: AvailableArtistsState,
) {
    companion object {
        val default
            get() = ReduxAppState(
                auth = AuthState.default,
                myOrders = OrdersState.default,
                availableArtists = AvailableArtistsState.default
            )
    }
}
