package io.gubarsergey.orders

import androidx.fragment.app.Fragment
import io.gubarsergey.ReduxAppState
import io.gubarsergey.defaultTag
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.configurators.Configurator
import io.gubarsergey.redux.redux.Observer

class OrdersConfigurator(private val core: ReduxCore<ReduxAppState>): Configurator() {

    private val connector = OrdersConnector(core)

    override fun subscribe(fragment: Fragment, hashCode: Int) {
        when (fragment) {
            is OrdersFragment -> {
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
            is OrdersFragment -> {
                core.store.removeObserver(connector.defaultTag, hashCode)
            }
        }
    }
}
