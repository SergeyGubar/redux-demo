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
                    ratingCount = stateArtist.ratingInfo.numberOfRatings,
                    makeAnOrder = Command.nop(),
                )
            }.appliedFilters(appState),
            viewLoaded = core.bind(LoadAvailableArtists),
            selectNoneFilter = core.bind(AvailableArtistsFilterReset),
            selectBestRatingFilter = core.bind(AvailableArtistsBestRatingSelected),
            selectMostOrdersFilter = core.bind(AvailableArtistsMostOrdersSelected),
            filter = when (appState.availableArtists.appliedFilter) {
                AvailableArtistsState.ArtistFilter.NONE        -> AvailableArtistsProps.Filter.NONE
                AvailableArtistsState.ArtistFilter.BEST_RATING -> AvailableArtistsProps.Filter.BEST_RATING
                AvailableArtistsState.ArtistFilter.MOST_ORDERS -> AvailableArtistsProps.Filter.MOST_ORDERS
            },
            chips = appState.availableArtists.genresSelection.mapValues { (genre, isChecked) ->
                AvailableArtistsProps.ChipInfo(core.bind(AvailableArtistsGenreSelection(genre)), isChecked)
            }
        )
    }

    private fun List<AvailableArtistsProps.ArtistProps>.appliedFilters(state: ReduxAppState): List<AvailableArtistsProps.ArtistProps> {
        val filtered = when (state.availableArtists.appliedFilter) {
            AvailableArtistsState.ArtistFilter.NONE        -> this
            AvailableArtistsState.ArtistFilter.BEST_RATING -> this.sortedByDescending { it.averageRating }
            AvailableArtistsState.ArtistFilter.MOST_ORDERS -> this.sortedByDescending { it.ratingCount }
        }

        val shouldFilterByGenre = state.availableArtists.genresSelection.any { it.value }
        val selectedGenres = state.availableArtists.genresSelection.filter { it.value }.map { it.key }

        val filteredByGenre = when {
            shouldFilterByGenre -> filtered.filter {
                it.genres.containsAll(selectedGenres)
            }
            else -> filtered
        }
        return filteredByGenre
    }
}
