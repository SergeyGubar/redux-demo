package io.gubarsergey.orders

data class AddRatingDto(
    val artistId: String,
    val rating: Int,
    val comment: String,
)
