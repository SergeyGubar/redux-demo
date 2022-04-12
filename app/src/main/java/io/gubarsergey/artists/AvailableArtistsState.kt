package io.gubarsergey.artists

import io.gubarsergey.redux.redux.Reduce
import io.gubarsergey.redux.redux.Reducer

data class AvailableArtistsState(
    val byId: MutableMap<String, Artist>
) {

    companion object {
        val default get() = AvailableArtistsState(mutableMapOf())
    }

    data class Artist(
        val id: String,
        val fullName: String,
        val email: String,
        val ratingInfo: RatingInfo,
        val profileDescription: String,
        val genres: List<String>
    ) {
        data class RatingInfo(
            val averageRating: Double,
            val numberOfRatings: Int,
        )
    }
}

val Reduce.availableArtists by Reducer<AvailableArtistsState> { state, action ->
    when (action) {
        is AvailableArtistsLoaded -> {
            action.dto.artists.forEach {
                state.byId[it._id] = AvailableArtistsState.Artist(
                    id = it._id,
                    fullName = it.firstName + " " + it.lastName,
                    email = it.email,
                    ratingInfo = AvailableArtistsState.Artist.RatingInfo(
                        averageRating = it.ratings.map { rating -> rating.rating }.average(),
                        numberOfRatings = it.ratings.size,
                    ),
                    profileDescription = it.profileDescription,
                    genres = it.genres
                )
            }
        }
    }
    state
}
