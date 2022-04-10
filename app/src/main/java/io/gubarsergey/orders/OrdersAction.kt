package io.gubarsergey.orders

import io.gubarsergey.orders.service.MyOrderDto
import io.gubarsergey.redux.redux.ReduxAction

data class OrdersLoaded(
    val orders: List<MyOrderDto>
): ReduxAction

object LoadMyOrders : ReduxAction
