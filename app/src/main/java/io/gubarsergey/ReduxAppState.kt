package io.gubarsergey

import io.gubarsergey.artists.AvailableArtistsState
import io.gubarsergey.auth.AuthState
import io.gubarsergey.orders.OrdersState
import io.gubarsergey.orders.confirm.OrderConfirmationState
import io.gubarsergey.orders.create.CreateOrderState
import io.gubarsergey.orders.result.SendOrderResultState

data class ReduxAppState(
    val auth: AuthState,
    val myOrders: OrdersState,
    val availableArtists: AvailableArtistsState,
    val createOrder: CreateOrderState,
    val orderConfirmation: OrderConfirmationState,
    val sendOrderResultState: SendOrderResultState,
) {
    companion object {
        val default
            get() = ReduxAppState(
                auth = AuthState.default,
                myOrders = OrdersState.default,
                availableArtists = AvailableArtistsState.default,
                createOrder = CreateOrderState.default,
                orderConfirmation = OrderConfirmationState.default,
                sendOrderResultState = SendOrderResultState.default,
            )
    }
}
