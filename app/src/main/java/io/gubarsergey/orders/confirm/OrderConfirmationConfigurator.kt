package io.gubarsergey.orders.confirm

import androidx.fragment.app.Fragment
import io.gubarsergey.BaseConnector
import io.gubarsergey.ReduxAppState
import io.gubarsergey.defaultTag
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.configurators.Configurator
import io.gubarsergey.redux.redux.Observer

class OrderConfirmationConfigurator(private val core: ReduxCore<ReduxAppState>) : Configurator() {

    private val connector = OrderConfirmationConnector(core)

    override fun subscribe(fragment: Fragment, hashCode: Int) {
        when (fragment) {
            is OrderConfirmationFragment -> {
                core.store.addObserver(
                    Observer(
                        { state -> fragment.props.value = connector.map(state) },
                        connector.defaultTag,
                        hashCode
                    )
                )
            }
        }
    }

    override fun unsubscribe(fragment: Fragment, hashCode: Int) {
        when (fragment) {
            is OrderConfirmationFragment -> core.store.removeObserver(connector.defaultTag, hashCode)
        }
    }
}

class OrderConfirmationConnector(private val core: ReduxCore<ReduxAppState>) : BaseConnector<OrderConfirmationProps>() {
    override fun map(appState: ReduxAppState): OrderConfirmationProps {
        val order =
            appState.myOrders.byId[appState.orderConfirmation.orderId] ?: error("No info for id ${appState.orderConfirmation.orderId}")
        return OrderConfirmationProps(
            resultUrl = order.resultUrl,
            rating = appState.orderConfirmation.rating,
            confirmOrder = core.bind(OrderConfirmationConfirmOrder),
            rejectOrder = core.bind(OrderConfirmationRejectOrder),
            orderDescription = order.comment,
            orderTo = order.userName,
            changeRating = core.bindWith { OrderConfirmationChangeRating(it) },
            changeComment = core.bindWith { OrderConfirmationChangeComment(it) }
        )
    }
}
