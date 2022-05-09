package io.gubarsergey.orders.service

data class PatchOrderDto(
    val orderId: String,
    val status: String,
    val resultUrl: String? = null,
)
