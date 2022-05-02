package io.gubarsergey.orders.create

import io.gubarsergey.artists.Genres
import io.gubarsergey.redux.redux.Reduce
import io.gubarsergey.redux.redux.Reducer

data class CreateOrderState(
    val artistId: String?,
    val comment: String,
    val bpm: Int,
    val deadline: String?,
    val genres: MutableMap<String, Boolean>,
) {
    companion object {
        val default
            get() = CreateOrderState(
                null, "", 0, null, mutableMapOf(
                    Genres.METAL to false,
                    Genres.POP to false,
                    Genres.FOLK to false,
                    Genres.HIP_HOP to false,
                    Genres.RAP to false,
                )
            )
    }
}

val Reduce.createOrder by Reducer<CreateOrderState> { state, action ->
    when (action) {
        is CreateOrderOpened           -> state.copy(artistId = action.artistId)
        is CreateOrderCommentUpdated   -> state.copy(comment = action.comment)
        is CreateOrderBpmUpdated       -> state.copy(bpm = action.bpm)
        is CreateOrderDeadlineSelected -> state.copy(deadline = action.deadline)
        is CreateOrderGenreSelection   -> {
            state.genres[action.genre] = !state.genres[action.genre]!!
            state
        }
        else                           -> state
    }
}
