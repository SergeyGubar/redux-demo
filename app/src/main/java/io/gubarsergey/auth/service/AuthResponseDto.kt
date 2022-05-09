package io.gubarsergey.auth.service

data class AuthResponseDto(
    val access_token: String,
    val role: String,
    val name: String,
)
