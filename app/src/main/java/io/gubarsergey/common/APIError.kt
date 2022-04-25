package io.gubarsergey.common

sealed class APIError {
    object General: APIError()
    object Unauthorized: APIError()
}
