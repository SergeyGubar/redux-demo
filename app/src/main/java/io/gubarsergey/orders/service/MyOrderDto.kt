package io.gubarsergey.orders.service

import com.squareup.moshi.Json

data class MyOrderDto(
    @field:Json(name = "order") val order: Order,
    @field:Json(name = "to") val to: To
)
