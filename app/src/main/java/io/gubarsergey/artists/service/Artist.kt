package io.gubarsergey.artists.service

data class Artist(
    val __v: Int,
    val _id: String,
    val email: String,
    val firstName: String,
    val genres: List<String>,
    val lastName: String,
    val password: String,
    val profileDescription: String,
    val ratings: List<Rating>
)
