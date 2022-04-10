package io.gubarsergey.orders.service

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class To(
    @field:Json(name = "email") val email: String,
    @field:Json(name = "id") val id: String,
    @field:Json(name = "lastName") val lastName: String,
    @field:Json(name = "name") val name: String
)
