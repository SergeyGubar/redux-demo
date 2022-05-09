package io.gubarsergey.orders.result

import androidx.fragment.app.Fragment
import io.gubarsergey.BaseConnector
import io.gubarsergey.ReduxAppState
import io.gubarsergey.defaultTag
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.configurators.Configurator
import io.gubarsergey.redux.redux.Observer

class SendOrderResultConfigurator(private val core: ReduxCore<ReduxAppState>) : Configurator() {

    private val connector = SendOrderResultConnector(core)

    override fun subscribe(fragment: Fragment, hashCode: Int) {
        when (fragment) {
            is SendOrderResultFragment -> {
                core.store.addObserver(
                    Observer(
                        { state -> fragment.render(connector.map(state)) },
                        connector.defaultTag,
                        hashCode
                    )
                )
            }
        }
    }

    override fun unsubscribe(fragment: Fragment, hashCode: Int) {
        when (fragment) {
            is SendOrderResultFragment -> {
                core.store.removeObserver(connector.defaultTag, hashCode)
            }
        }
    }
}

class SendOrderResultConnector(private val core: ReduxCore<ReduxAppState>) : BaseConnector<SendOrderResultProps>() {
    override fun map(appState: ReduxAppState): SendOrderResultProps {
        val state = appState.sendOrderResultState
        val order = appState.myOrders.byId[state.orderId] ?: error("No order for id ${state.orderId}")
        return SendOrderResultProps(
            send = core.bindWith { SendOrderResult(it) }, from = order.userName, description = order.comment
        )
    }
}
