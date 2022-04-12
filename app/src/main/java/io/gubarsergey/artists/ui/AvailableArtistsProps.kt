package io.gubarsergey.artists.ui

import io.gubarsergey.redux.operations.Command

data class AvailableArtistsProps(
    val artists: List<ArtistProps>,
    val viewLoaded: Command,
) {

    data class ArtistProps(
        val id: String,
        val fullName: String,
        val profileDescription: String,
        val genres: List<String>,
        val averageRating: Double,
        val ratingCount: Int,
        val email: String,
    )
}
