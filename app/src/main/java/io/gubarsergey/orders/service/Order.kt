package io.gubarsergey.orders.service

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Order(
    @field:Json(name = "__v") val __v: Int,
    @field:Json(name = "_id") val _id: String,
    @field:Json(name = "artistId") val artistId: String,
    @field:Json(name = "bpm") val bpm: String,
    @field:Json(name = "comment") val comment: String,
    @field:Json(name = "datePlaced") val datePlaced: String,
    @field:Json(name = "deadline") val deadline: String,
    @field:Json(name = "genre") val genre: List<String>,
    @field:Json(name = "resultUrl") val resultUrl: String?,
    @field:Json(name = "status") val status: String,
    @field:Json(name = "userId") val userId: String,
)
