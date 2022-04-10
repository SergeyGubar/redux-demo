package io.gubarsergey

import io.gubarsergey.auth.AuthState
import io.gubarsergey.orders.OrdersState

data class ReduxAppState(
    val auth: AuthState,
    val myOrders: OrdersState,
)
