package io.gubarsergey.orders

import io.gubarsergey.BaseConnector
import io.gubarsergey.ReduxAppState
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.operations.Command

class OrdersConnector(private val core: ReduxCore<ReduxAppState>) : BaseConnector<OrdersProps>() {
    override fun map(appState: ReduxAppState): OrdersProps {
        return OrdersProps(
            viewLoaded = core.bind(LoadMyOrders),
            orders = appState.myOrders.byId.map { (id, order) ->
                OrdersProps.Order(
                    id = id,
                    artistName = order.artistName,
                    placedDate = order.placedDate,
                    deadline = order.deadLine,
                    status = OrderStatus.fromString(order.status),
                    comment = order.comment,
                    bpm = order.bpm,
                    genres = order.genres,
                    goToDetails = Command(action = {
                        TODO()
                    })
                )
            }
        )
    }
}
