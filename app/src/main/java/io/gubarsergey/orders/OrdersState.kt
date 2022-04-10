package io.gubarsergey.orders

import io.gubarsergey.redux.redux.Reduce
import io.gubarsergey.redux.redux.Reducer

data class OrdersState(
    val byId: MutableMap<String, Order>
) {

    companion object {
        val default get() = OrdersState(mutableMapOf())
    }

    data class Order(
        val userId: String,
        val artistId: String,
        val artistName: String,
        val genres: List<String>,
        val placedDate: String,
        val deadLine: String,
        val status: String,
        val comment: String,
        val resultUrl: String,
        val bpm: String,
    )
}

val Reduce.orders by Reducer<OrdersState> { state, action ->
    when (action) {
        is OrdersLoaded -> {
            action.orders.forEach {
                state.byId[it.order._id] = OrdersState.Order(
                    userId = it.order.userId,
                    artistId = it.to.id,
                    genres = it.order.genre,
                    deadLine = it.order.deadline,
                    status = it.order.status,
                    comment = it.order.comment,
                    resultUrl = it.order.resultUrl,
                    bpm = it.order.bpm,
                    artistName = it.to.name + " " + it.to.lastName,
                    placedDate = it.order.datePlaced,
                )
            }
        }
    }
    state
}
