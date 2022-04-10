package io.gubarsergey.orders.service

data class NewOrderRequestDto(
    val artistId: String,
    val bpm: String,
    val comment: String,
    val date: String,
    val genre: List<String>
)
