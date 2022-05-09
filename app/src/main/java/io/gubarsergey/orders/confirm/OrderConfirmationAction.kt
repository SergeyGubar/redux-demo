package io.gubarsergey.orders.confirm

import io.gubarsergey.redux.redux.ReduxAction

data class GoToOrderConfirmation(
    val id: String
) : ReduxAction

data class OrderConfirmationChangeRating(
    val rating: Int,
) : ReduxAction

data class OrderConfirmationChangeComment(
    val comment: String,
) : ReduxAction

object OrderConfirmationConfirmOrder : ReduxAction
object OrderConfirmationRejectOrder : ReduxAction
