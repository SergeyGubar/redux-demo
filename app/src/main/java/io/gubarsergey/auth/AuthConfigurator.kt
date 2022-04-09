package io.gubarsergey.auth

import androidx.fragment.app.Fragment
import io.gubarsergey.ReduxAppState
import io.gubarsergey.auth.ui.AuthFragment
import io.gubarsergey.defaultTag
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.configurators.Configurator
import io.gubarsergey.redux.redux.Observer

class AuthConfigurator(private val core: ReduxCore<ReduxAppState>) : Configurator() {

    private val connector = AuthConnector(core)

    override fun subscribe(fragment: Fragment, hashCode: Int) {
        when (fragment) {
            is AuthFragment -> {
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
            is AuthFragment -> {
                core.store.removeObserver(connector.defaultTag, hashCode)
            }
        }
    }
}
