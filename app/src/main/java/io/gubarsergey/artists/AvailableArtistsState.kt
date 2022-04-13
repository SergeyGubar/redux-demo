package io.gubarsergey.artists

import io.gubarsergey.redux.redux.Reduce
import io.gubarsergey.redux.redux.Reducer

data class AvailableArtistsState(
    val byId: MutableMap<String, Artist>,
    val appliedFilter: ArtistFilter,
    val genresSelection: MutableMap<String, Boolean>,
) {

    companion object {
        val default get() = AvailableArtistsState(mutableMapOf(), ArtistFilter.NONE, mutableMapOf(
            Genres.METAL to false,
            Genres.POP to false,
            Genres.FOLK to false,
            Genres.HIP_HOP to false,
            Genres.RAP to false,
        ))
    }

    enum class ArtistFilter {
        NONE,
        BEST_RATING,
        MOST_ORDERS
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
        is AvailableArtistsLoaded             -> {
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
            state
        }
        is AvailableArtistsBestRatingSelected -> state.copy(appliedFilter = AvailableArtistsState.ArtistFilter.BEST_RATING)
        is AvailableArtistsMostOrdersSelected -> state.copy(appliedFilter = AvailableArtistsState.ArtistFilter.MOST_ORDERS)
        is AvailableArtistsFilterReset        -> state.copy(appliedFilter = AvailableArtistsState.ArtistFilter.NONE)
        is AvailableArtistsGenreSelection -> {
            state.genresSelection[action.genre] = !state.genresSelection[action.genre]!!
            state
        }
        else                                  -> state
    }
}
