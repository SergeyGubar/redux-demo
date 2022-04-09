package io.gubarsergey.counter

import androidx.fragment.app.Fragment
import io.gubarsergey.ReduxAppState
import io.gubarsergey.defaultTag
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.configurators.Configurator
import io.gubarsergey.redux.redux.Observer

class CounterConfigurator(
    private val core: ReduxCore<ReduxAppState>
) : Configurator() {

    private val counterConnector = CounterConnector(core)

    override fun subscribe(fragment: Fragment, hashCode: Int) {
        when (fragment) {
            is CounterFragment -> {
                core.store.addObserver(Observer(
                    { state -> fragment.render(counterConnector.map(state)) },
                    counterConnector.defaultTag,
                    hashCode
                ))
            }
        }
    }

    override fun unsubscribe(fragment: Fragment, hashCode: Int) {
        when (fragment) {
            is CounterFragment -> {
                this.core.store.removeObserver(counterConnector.defaultTag, fragment.hashCode())
            }
        }
    }
}
