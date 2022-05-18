package io.gubarsergey.orders

import io.gubarsergey.BaseConnector
import io.gubarsergey.ReduxAppState
import io.gubarsergey.Router
import io.gubarsergey.auth.AuthState
import io.gubarsergey.orders.confirm.GoToOrderConfirmation
import io.gubarsergey.orders.result.OpenSendOrderResult
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.operations.Command

class OrdersConnector(private val core: ReduxCore<ReduxAppState>) : BaseConnector<OrdersProps>() {
    override fun map(appState: ReduxAppState): OrdersProps {

        return OrdersProps(
            viewLoaded = core.bind(LoadMyOrders),
            orders = when (appState.myOrders.loadingStatus) {
                OrdersState.LoadingStatus.IDLE -> OrdersProps.Orders.Idle
                OrdersState.LoadingStatus.IN_PROGRESS -> OrdersProps.Orders.Loading
                OrdersState.LoadingStatus.SUCCESS -> OrdersProps.Orders.Loaded(
                    appState.myOrders.byId.map { (id, order) ->
                        OrdersProps.Order(
                            id = id,
                            artistName = order.userName,
                            placedDate = order.placedDate,
                            deadline = order.deadLine,
                            status = OrderStatus.fromString(order.status),
                            comment = order.comment,
                            bpm = order.bpm,
                            genres = order.genres,
                            goToDetails = Command(action = {
                                if (appState.auth.userRole == AuthState.UserRole.CUSTOMER) {
                                    if (OrderStatus.fromString(order.status) == OrderStatus.AwaitingConfirmation) {
                                        core.dispatch(GoToOrderConfirmation(id))
                                        Router.goToOrderConfirmation()
                                    }
                                }
                                if (appState.auth.userRole == AuthState.UserRole.ARTIST) {
                                    if (OrderStatus.fromString(order.status) == OrderStatus.InProgress) {
                                        core.dispatch(OpenSendOrderResult(id))
                                        Router.goToSendOrderResult()
                                    }
                                }
                            }),
                            userRole = appState.auth.userRole,
                            accept = core.bind(ArtistAcceptOrder(id)),
                            reject = core.bind(ArtistRejectOrder(id))
                        )
                    }
                )
                OrdersState.LoadingStatus.FAILED -> OrdersProps.Orders.LoadingFailed
            }
        )
    }
}
