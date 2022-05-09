package io.gubarsergey.orders.result

import io.gubarsergey.redux.redux.Reduce
import io.gubarsergey.redux.redux.Reducer

data class SendOrderResultState(
    val orderId: String,
) {
    companion object {
        val default get() = SendOrderResultState(orderId = "")
    }
}

val Reduce.sendOrderResult by Reducer<SendOrderResultState> { state, action ->
    when (action) {
        is OpenSendOrderResult -> state.copy(orderId = action.orderId)
        else                   -> state
    }
}
