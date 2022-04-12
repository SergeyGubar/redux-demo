package io.gubarsergey.artists.service

data class Rating(
    val _id: String,
    val comment: String,
    val rating: Int,
    val timestamp: String,
    val userId: String
)
