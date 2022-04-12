package io.gubarsergey.artists

import androidx.fragment.app.Fragment
import io.gubarsergey.BaseConnector
import io.gubarsergey.ReduxAppState
import io.gubarsergey.artists.ui.AvailableArtistsFragment
import io.gubarsergey.artists.ui.AvailableArtistsProps
import io.gubarsergey.defaultTag
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.configurators.Configurator
import io.gubarsergey.redux.operations.Command
import io.gubarsergey.redux.redux.Observer

class AvailableArtistsConfigurator(private val core: ReduxCore<ReduxAppState>) : Configurator() {

    private val connector = AvailableArtistsConnector(core)

    override fun subscribe(fragment: Fragment, hashCode: Int) {
        when (fragment) {
            is AvailableArtistsFragment -> {
                core.store.addObserver(
                    Observer(
                        { state -> fragment.props.value = connector.map(state) },
                        connector.defaultTag,
                        hashCode
                    )
                )
            }
        }
    }

    override fun unsubscribe(fragment: Fragment, hashCode: Int) {
        when (fragment) {
            is AvailableArtistsFragment -> core.store.removeObserver(connector.defaultTag, hashCode)
        }
    }
}

class AvailableArtistsConnector(private val core: ReduxCore<ReduxAppState>) : BaseConnector<AvailableArtistsProps>() {
    override fun map(appState: ReduxAppState): AvailableArtistsProps {
        return AvailableArtistsProps(
            artists = core.state.availableArtists.byId.map { (id, stateArtist) ->
                AvailableArtistsProps.ArtistProps(
                    id = id,
                    fullName = stateArtist.fullName,
                    profileDescription = stateArtist.profileDescription,
                    email = stateArtist.email,
                    genres = stateArtist.genres,
                    averageRating = stateArtist.ratingInfo.averageRating,
                    ratingCount = stateArtist.ratingInfo.numberOfRatings
                )
            },
            viewLoaded = Command(action = { core.dispatch(LoadAvailableArtists) }),
        )
    }
}
