package io.gubarsergey.orders.service

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class MyOrdersDto(@field:Json(name = "orders") val orders: List<MyOrderDto>)
