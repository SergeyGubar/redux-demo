package io.gubarsergey.orders.result

import io.gubarsergey.redux.redux.ReduxAction

data class SendOrderResult(
    val result: String
): ReduxAction

data class OpenSendOrderResult(
    val orderId: String,
): ReduxAction
