package io.gubarsergey.artists.service

import retrofit2.http.GET
import retrofit2.http.Header

interface AvailableArtistsAPI {
    @GET("api/artist")
    suspend fun getAvailableArtists(@Header("Authorization") token: String): AvailableArtistsResponseDto
}
