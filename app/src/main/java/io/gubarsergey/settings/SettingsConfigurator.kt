package io.gubarsergey.settings

import androidx.fragment.app.Fragment
import io.gubarsergey.BaseConnector
import io.gubarsergey.ReduxAppState
import io.gubarsergey.defaultTag
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.configurators.Configurator
import io.gubarsergey.redux.redux.Observer

class SettingsConfigurator(private val core: ReduxCore<ReduxAppState>) : Configurator() {

    private val connector = SettingsConnector(core)

    override fun subscribe(fragment: Fragment, hashCode: Int) {
        when (fragment) {
            is SettingsFragment -> {
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
            is SettingsFragment -> core.store.removeObserver(connector.defaultTag, hashCode)
        }
    }
}

class SettingsConnector(private val core: ReduxCore<ReduxAppState>) : BaseConnector<SettingsProps>() {
    override fun map(appState: ReduxAppState): SettingsProps {
        return SettingsProps(
            name = appState.auth.name, email = appState.auth.email, role = appState.auth.userRole.toString(), logout = core.bind(Logout)
        )
    }
}
