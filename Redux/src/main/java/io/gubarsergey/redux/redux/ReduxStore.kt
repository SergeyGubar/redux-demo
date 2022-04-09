package io.gubarsergey.redux.redux

import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.Middleware
import io.gubarsergey.redux.operations.Command

class ReduxStore<AppState>(
    initialState: AppState,
    private val core: ReduxCore<AppState>,
    private val reducer: Reducer<AppState>
) {

    private var state = initialState
    private val observers = mutableSetOf<Observer<AppState>>()
    private val middlewares = mutableSetOf<Middleware>()

    fun appState() = state

    fun addMiddlewares(newMiddlewares: Iterable<Middleware>) {
        this.middlewares.addAll(newMiddlewares)
    }

    fun dispatch(action: ReduxAction) {
        core.runOnUiThread {
            state = reducer.reduce(state, action)
            observers.forEach { it.invoke(state) }
            middlewares.forEach { middleware ->
                middleware.apply(action)
            }
        }
    }

    fun addObserver(observer: Observer<AppState>) {
        require(observer !in observers) {
            "io.gubarsergey.redux.redux.Observer $observer is already subscribed for updates of state"
        }
        observers += observer
        observer(state)
    }

    fun removeObserver(tag: String, hashCode: Int) {
        observers.removeAll { it.tag == tag && it.hashCode == hashCode }
    }

    fun <T> dispatchWith(actionCreator: (T) -> ReduxAction) =
        Command.With<T> { dispatch(actionCreator(it)) }

    fun <T> bind(action: ReduxAction) = Command { dispatch(action) }
}
