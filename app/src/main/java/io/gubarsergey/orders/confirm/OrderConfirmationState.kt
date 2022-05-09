package io.gubarsergey.orders.confirm

import io.gubarsergey.redux.redux.Reduce
import io.gubarsergey.redux.redux.Reducer

data class OrderConfirmationState(
    val orderId: String?,
    val rating: Int,
    val comment: String,
) {
    companion object {
        val default get() = OrderConfirmationState(orderId = null, rating = 0, comment = "")
    }
}

val Reduce.orderConfirmation by Reducer<OrderConfirmationState> { state, action ->
    when (action) {
        is GoToOrderConfirmation          -> state.copy(orderId = action.id)
        is OrderConfirmationChangeComment -> state.copy(comment = action.comment)
        is OrderConfirmationChangeRating  -> state.copy(rating = action.rating)
        else                              -> state
    }
}


