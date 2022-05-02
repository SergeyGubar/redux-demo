package io.gubarsergey.orders.service

data class CreateOrderDto(
    val artistId: String,
    val bpm: String,
    val genre: List<String>,
    val comment: String,
    val date: String,
)
