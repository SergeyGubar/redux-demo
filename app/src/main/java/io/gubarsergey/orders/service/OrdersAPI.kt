package io.gubarsergey.orders.service

import io.gubarsergey.orders.AddRatingDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface OrdersAPI {
    @GET("order")
    suspend fun myOrders(@Header("Authorization") token: String): MyOrdersDto

    @POST("order")
    suspend fun createOrder(@Header("Authorization") token: String, @Body dto: CreateOrderDto)

    @PATCH("order")
    suspend fun patchOrder(@Header("Authorization") token: String, @Body dto: PatchOrderDto)

    @POST("api/artist/rating")
    suspend fun addRating(@Header("Authorization") token: String, @Body dto: AddRatingDto)
}
