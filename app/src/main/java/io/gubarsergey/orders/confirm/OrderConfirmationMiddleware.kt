package io.gubarsergey.orders.confirm

import io.gubarsergey.ReduxAppState
import io.gubarsergey.Router
import io.gubarsergey.orders.AddRatingDto
import io.gubarsergey.orders.LoadMyOrders
import io.gubarsergey.orders.OrderStatus
import io.gubarsergey.orders.service.OrdersAPI
import io.gubarsergey.orders.service.PatchOrderDto
import io.gubarsergey.redux.Middleware
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.redux.ReduxAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.inject

class OrderConfirmationMiddleware(private val core: ReduxCore<ReduxAppState>) : Middleware {

    private val api: OrdersAPI by inject()

    override fun apply(action: ReduxAction) {
        when (action) {
            is OrderConfirmationConfirmOrder -> {
                launch {

                    val orderConfirmationState = core.state.orderConfirmation
                    val order = core.state.myOrders.byId[orderConfirmationState.orderId]!!
                    val statusChangeResult = withContext(Dispatchers.IO) {
                        runCatching {

                            val dto = PatchOrderDto(
                                orderId = orderConfirmationState.orderId!!,
                                status = OrderStatus.Completed.toNetworkString()
                            )
                            api.patchOrder(core.state.auth.token.bearerFormatted, dto)
                        }
                    }
                    val ratingChangeResult = withContext(Dispatchers.IO) {
                        runCatching {
                            val dto = AddRatingDto(
                                artistId = order.artistId,
                                rating = orderConfirmationState.rating,
                                comment = orderConfirmationState.comment
                            )
                            api.addRating(core.state.auth.token.bearerFormatted, dto)
                        }
                    }

                    if (statusChangeResult.isSuccess && ratingChangeResult.isSuccess) {
                        core.dispatch(LoadMyOrders)
                        Router.goBack()
                    }

                }
            }
            is OrderConfirmationRejectOrder  -> {
                launch {
                    val orderConfirmationState = core.state.orderConfirmation
                    val statusChangeResult = withContext(Dispatchers.IO) {
                        runCatching {
                            val dto = PatchOrderDto(
                                orderId = orderConfirmationState.orderId!!,
                                status = OrderStatus.Rejected.toNetworkString()
                            )
                            api.patchOrder(core.state.auth.token.bearerFormatted, dto)
                        }
                    }
                    statusChangeResult.fold(
                        onSuccess = {
                            core.dispatch(LoadMyOrders)
                            Router.goBack()
                        },
                        onFailure = {}
                    )
                }
            }
        }
    }
}
