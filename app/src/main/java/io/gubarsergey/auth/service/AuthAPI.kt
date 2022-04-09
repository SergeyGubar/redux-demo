package io.gubarsergey.auth.service

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {

    @POST("auth/login")
    suspend fun login(@Body request: AuthRequestDto): AuthResponseDto
}
