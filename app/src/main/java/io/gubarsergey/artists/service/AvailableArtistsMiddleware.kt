package io.gubarsergey.artists.service

import io.gubarsergey.ReduxAppState
import io.gubarsergey.artists.AvailableArtistsLoadFailed
import io.gubarsergey.artists.AvailableArtistsLoaded
import io.gubarsergey.artists.LoadAvailableArtists
import io.gubarsergey.redux.Middleware
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.redux.ReduxAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.inject

class AvailableArtistsMiddleware(private val core: ReduxCore<ReduxAppState>) : Middleware {

    private val api: AvailableArtistsAPI by inject()

    override fun apply(action: ReduxAction) {
        when (action) {
            is LoadAvailableArtists -> {
                launch {
                    val result = withContext(Dispatchers.IO) {
                        runCatching {
                            api.getAvailableArtists(core.state.auth.token.bearerFormatted)
                        }
                    }
                    result.fold(
                        onSuccess = { core.dispatch(AvailableArtistsLoaded(it)) },
                        onFailure = { core.dispatch(AvailableArtistsLoadFailed) }
                    )
                }
            }
        }
    }
}
