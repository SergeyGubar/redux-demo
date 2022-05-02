package io.gubarsergey.orders.create

import androidx.fragment.app.Fragment
import io.gubarsergey.BaseConnector
import io.gubarsergey.ReduxAppState
import io.gubarsergey.defaultTag
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.configurators.Configurator
import io.gubarsergey.redux.redux.Observer

class CreateOrderConfigurator(private val core: ReduxCore<ReduxAppState>) : Configurator() {

    private val connector = CreateOrderConnector(core)

    override fun subscribe(fragment: Fragment, hashCode: Int) {
        when (fragment) {
            is CreateOrderFragment -> {
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
            is CreateOrderFragment -> {
                core.store.removeObserver(connector.defaultTag, hashCode)
            }
        }
    }
}

class CreateOrderConnector(private val core: ReduxCore<ReduxAppState>) : BaseConnector<CreateOrderProps>() {
    override fun map(appState: ReduxAppState): CreateOrderProps {

        val artist = appState.availableArtists.byId[appState.createOrder.artistId] ?: return CreateOrderProps.NotAvailable

        return CreateOrderProps.Loaded(
            artistName = artist.fullName,
            chips = appState.createOrder.genres.mapValues { (name, isSelected) ->
                CreateOrderProps.Loaded.ChipInfo(isSelected = isSelected, select = core.bind(CreateOrderGenreSelection(name)), title = name)
            },
            deadline = appState.createOrder.deadline,
            makeAnOrder = core.bind(CreateOrderSaveAction),
            bpmUpdated = core.bindWith { CreateOrderBpmUpdated(it) },
            commentUpdated = core.bindWith { CreateOrderCommentUpdated(it) },
            deadlineUpdated = core.bindWith { CreateOrderDeadlineSelected(it) }
        )
    }
}
