package io.gubarsergey.orders

import io.gubarsergey.ReduxAppState
import io.gubarsergey.orders.service.OrdersAPI
import io.gubarsergey.redux.Middleware
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.redux.ReduxAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.inject

class OrdersMiddleware(private val core: ReduxCore<ReduxAppState>) : Middleware {

    private val api: OrdersAPI by inject()

    override fun apply(action: ReduxAction) {
        when (action) {
            is LoadMyOrders -> {
                launch {
                    withContext(Dispatchers.IO) {
                        val token = "Bearer ${core.state.auth.token}"
                        val orders = api.myOrders(token)
                        core.dispatch(OrdersLoaded(orders.orders))
                    }
                }
            }
        }
    }
}
