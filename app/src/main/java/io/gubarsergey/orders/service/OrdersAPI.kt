package io.gubarsergey.orders.service

import retrofit2.http.GET
import retrofit2.http.Header

interface OrdersAPI {
    @GET("order")
    suspend fun myOrders(@Header("Authorization") token: String): MyOrdersDto
}
