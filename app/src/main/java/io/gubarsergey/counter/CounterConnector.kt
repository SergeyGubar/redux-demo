package io.gubarsergey.counter

import io.gubarsergey.ReduxAppState
import io.gubarsergey.BaseConnector
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.operations.Command

class CounterConnector(private val core: ReduxCore<ReduxAppState>) : BaseConnector<CounterProps>() {
    override fun map(appState: ReduxAppState): CounterProps {
        return CounterProps(1, Command {
            core.dispatch(IncrementCounterAction)
        })
    }
}
