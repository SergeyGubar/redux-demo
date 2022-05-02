package io.gubarsergey.orders.create

import io.gubarsergey.ReduxAppState
import io.gubarsergey.Router
import io.gubarsergey.orders.service.CreateOrderDto
import io.gubarsergey.orders.service.OrdersAPI
import io.gubarsergey.redux.Middleware
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.redux.ReduxAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.inject
import timber.log.Timber

class CreateOrderMiddleware(private val core: ReduxCore<ReduxAppState>) : Middleware {

    private val api: OrdersAPI by inject()

    override fun apply(action: ReduxAction) {
        when (action) {
            is CreateOrderSaveAction -> {
                val artistId = core.state.createOrder.artistId ?: return
                launch {

                    val result = withContext(Dispatchers.IO) {
                        val dto = CreateOrderDto(
                            artistId = artistId,
                            bpm = "${core.state.createOrder.bpm} BPM",
                            genre = core.state.createOrder.genres.filter { (_, isSelected) -> isSelected }.map { it.key },
                            comment = core.state.createOrder.comment,
                            date = core.state.createOrder.deadline ?: ""
                        )
                        runCatching {
                            api.createOrder(
                                core.state.auth.token.bearerFormatted, dto
                            )
                        }
                    }
                    result.fold(
                        onSuccess = {
                            Router.goBack()
                        },
                        onFailure = {
                            Timber.e("Error saving result $it")
                        }
                    )
                }
            }
        }
    }
}
