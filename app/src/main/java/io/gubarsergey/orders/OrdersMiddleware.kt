package io.gubarsergey.orders

import io.gubarsergey.ReduxAppState
import io.gubarsergey.Router
import io.gubarsergey.orders.result.SendOrderResult
import io.gubarsergey.orders.service.OrdersAPI
import io.gubarsergey.orders.service.PatchOrderDto
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
                        val token = core.state.auth.token.bearerFormatted
                        val result = runCatching {
                            api.myOrders(token)
                        }
                        result.fold(
                            onSuccess = {
                                core.dispatch(CustomerOrdersLoaded(it.orders))
                            },
                            onFailure = {
                                core.dispatch(OrdersLoadFailed)
                            }
                        )
                    }
                }
            }
            is ArtistAcceptOrder -> {
                launch {
                    val result = withContext(Dispatchers.IO) {
                        runCatching {
                            api.patchOrder(
                                core.state.auth.token.bearerFormatted, PatchOrderDto(
                                    orderId = action.id, status = OrderStatus.InProgress.toNetworkString()
                                )
                            )
                        }
                    }
                    if (result.isSuccess) {
                        core.dispatch(LoadMyOrders)
                    }
                }
            }
            is ArtistRejectOrder -> {
                launch {
                    val result = withContext(Dispatchers.IO) {
                        runCatching {
                            api.patchOrder(
                                core.state.auth.token.bearerFormatted, PatchOrderDto(
                                    orderId = action.id, status = OrderStatus.Rejected.toNetworkString()
                                )
                            )
                        }
                    }
                    if (result.isSuccess) {
                        core.dispatch(LoadMyOrders)
                    }
                }
            }
            is SendOrderResult -> {
                launch {
                    val result = withContext(Dispatchers.IO) {
                        runCatching {
                            api.patchOrder(
                                core.state.auth.token.bearerFormatted, PatchOrderDto(
                                    orderId = core.state.sendOrderResultState.orderId,
                                    status = OrderStatus.AwaitingConfirmation.toNetworkString(),
                                    resultUrl = action.result,
                                )
                            )
                        }
                    }
                    if (result.isSuccess) {
                        core.dispatch(LoadMyOrders)
                        Router.goBack()
                    }
                }
            }
        }
    }
}
