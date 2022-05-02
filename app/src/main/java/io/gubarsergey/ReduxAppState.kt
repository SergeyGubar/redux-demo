package io.gubarsergey

import io.gubarsergey.artists.AvailableArtistsState
import io.gubarsergey.auth.AuthState
import io.gubarsergey.orders.OrdersState
import io.gubarsergey.orders.create.CreateOrderState

data class ReduxAppState(
    val auth: AuthState,
    val myOrders: OrdersState,
    val availableArtists: AvailableArtistsState,
    val createOrder: CreateOrderState,
) {
    companion object {
        val default
            get() = ReduxAppState(
                auth = AuthState.default,
                myOrders = OrdersState.default,
                availableArtists = AvailableArtistsState.default,
                createOrder = CreateOrderState.default,
            )
    }
}
