package io.gubarsergey.artists

import io.gubarsergey.artists.service.AvailableArtistsResponseDto
import io.gubarsergey.redux.redux.ReduxAction

object LoadAvailableArtists : ReduxAction

data class AvailableArtistsLoaded(val dto: AvailableArtistsResponseDto) : ReduxAction
object AvailableArtistsLoadFailed : ReduxAction
