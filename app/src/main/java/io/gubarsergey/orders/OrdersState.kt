package io.gubarsergey.orders

import io.gubarsergey.redux.redux.Reduce
import io.gubarsergey.redux.redux.Reducer

data class OrdersState(
    val byId: MutableMap<String, Order>,
    val loadingStatus: LoadingStatus
) {

    enum class LoadingStatus {
        IDLE,
        IN_PROGRESS,
        SUCCESS,
        FAILED,
    }

    companion object {
        val default get() = OrdersState(mutableMapOf(), LoadingStatus.IDLE)
    }

    data class Order(
        val userId: String,
        val artistId: String,
        val userName: String,
        val genres: List<String>,
        val placedDate: String,
        val deadLine: String,
        val status: String,
        val comment: String,
        val resultUrl: String?,
        val bpm: String,
    )
}

val Reduce.orders by Reducer<OrdersState> { state, action ->
    when (action) {
        is CustomerOrdersLoaded      -> {

            action.orders.forEach {
                if (it.to != null) {
                    state.byId[it.order._id] = OrdersState.Order(
                        userId = it.order.userId,
                        artistId = it.to.id,
                        genres = it.order.genre,
                        deadLine = it.order.deadline,
                        status = it.order.status,
                        comment = it.order.comment,
                        resultUrl = it.order.resultUrl,
                        bpm = it.order.bpm,
                        userName = it.to.name + " " + it.to.lastName,
                        placedDate = it.order.datePlaced,
                    )
                } else {
                    state.byId[it.order._id] = OrdersState.Order(
                        userId = it.order.userId,
                        artistId = it.from.id,
                        genres = it.order.genre,
                        deadLine = it.order.deadline,
                        status = it.order.status,
                        comment = it.order.comment,
                        resultUrl = it.order.resultUrl,
                        bpm = it.order.bpm,
                        userName = it.from.name + " " + it.from.lastName,
                        placedDate = it.order.datePlaced,
                    )
                }

            }

            state
        }
        is ChangeOrdersLoadingStatus -> state.copy(loadingStatus = action.status)
        else                         -> state
    }
}
