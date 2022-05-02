package io.gubarsergey.orders.service

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface OrdersAPI {
    @GET("order")
    suspend fun myOrders(@Header("Authorization") token: String): MyOrdersDto

    @POST("order")
    suspend fun createOrder(@Header("Authorization") token: String, @Body dto: CreateOrderDto)
}
