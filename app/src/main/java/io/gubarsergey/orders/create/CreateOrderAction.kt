package io.gubarsergey.orders.create

import io.gubarsergey.redux.redux.ReduxAction

data class CreateOrderOpened(
    val artistId: String
) : ReduxAction

object CreateOrderSaveAction : ReduxAction

data class CreateOrderBpmUpdated(val bpm: Int) : ReduxAction
data class CreateOrderCommentUpdated(val comment: String) : ReduxAction
data class CreateOrderDeadlineSelected(val deadline: String) : ReduxAction
data class CreateOrderGenreSelection(val genre: String) : ReduxAction
