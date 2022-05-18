package io.gubarsergey.orders

import io.gubarsergey.orders.service.MyOrderDto
import io.gubarsergey.redux.redux.ReduxAction

data class CustomerOrdersLoaded(
    val orders: List<MyOrderDto>
) : ReduxAction

data class ChangeOrdersLoadingStatus(
    val status: OrdersState.LoadingStatus
) : ReduxAction

object LoadMyOrders : ReduxAction

data class ArtistAcceptOrder(
    val id: String
) : ReduxAction

data class ArtistRejectOrder(
    val id: String
) : ReduxAction
