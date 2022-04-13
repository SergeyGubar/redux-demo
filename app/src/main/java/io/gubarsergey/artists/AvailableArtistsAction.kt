package io.gubarsergey.artists

import io.gubarsergey.artists.service.AvailableArtistsResponseDto
import io.gubarsergey.redux.redux.ReduxAction

object LoadAvailableArtists : ReduxAction

object AvailableArtistsBestRatingSelected : ReduxAction
object AvailableArtistsMostOrdersSelected : ReduxAction
object AvailableArtistsFilterReset : ReduxAction


data class AvailableArtistsLoaded(val dto: AvailableArtistsResponseDto) : ReduxAction
object AvailableArtistsLoadFailed : ReduxAction

data class AvailableArtistsGenreSelection(val genre: String): ReduxAction
