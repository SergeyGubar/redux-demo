package io.gubarsergey.artists.ui

import io.gubarsergey.redux.operations.Command

data class AvailableArtistsProps(
    val artists: List<ArtistProps>,
    val viewLoaded: Command,
    val selectNoneFilter: Command,
    val selectBestRatingFilter: Command,
    val selectMostOrdersFilter: Command,
    val filter: Filter,
    val chips: Map<String, ChipInfo>
) {

    data class ChipInfo(
        val click: Command,
        val isSelected: Boolean,
    )

    enum class Filter {
        NONE,
        BEST_RATING,
        MOST_ORDERS
    }

    data class ArtistProps(
        val id: String,
        val fullName: String,
        val profileDescription: String,
        val genres: List<String>,
        val averageRating: Double,
        val ratingCount: Int,
        val email: String,
        val makeAnOrder: Command,
    ) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ArtistProps

            if (id != other.id) return false
            if (fullName != other.fullName) return false
            if (profileDescription != other.profileDescription) return false
            if (genres != other.genres) return false
            if (averageRating != other.averageRating) return false
            if (ratingCount != other.ratingCount) return false
            if (email != other.email) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id.hashCode()
            result = 31 * result + fullName.hashCode()
            result = 31 * result + profileDescription.hashCode()
            result = 31 * result + genres.hashCode()
            result = 31 * result + averageRating.hashCode()
            result = 31 * result + ratingCount
            result = 31 * result + email.hashCode()
            return result
        }
    }
}
