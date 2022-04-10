package io.gubarsergey.redux

import io.gubarsergey.redux.redux.Reduce
import io.gubarsergey.redux.redux.ReduxStore
import androidx.fragment.app.Fragment
import io.gubarsergey.redux.configurators.Configurator
import io.gubarsergey.redux.operations.ActivityOperations
import io.gubarsergey.redux.operations.Command
import io.gubarsergey.redux.redux.Reducer
import io.gubarsergey.redux.redux.ReduxAction
import org.koin.core.component.KoinComponent

class ReduxCore<AppState>(
    defaultState: AppState,
    private val applyReduce: (AppState, ReduxAction) -> AppState,
    private val dispatchOnUiThread: (() -> Unit) -> Unit
) {

    private val Reduce.state by Reducer<AppState> { state, action ->
        applyReduce(state, action)
    }

    val store = ReduxStore(
        defaultState,
        this,
        Reduce.state
    )

    val state get() = store.appState()

    private val configurators = mutableListOf<Configurator>()

    fun withConfigurators(newConfigurators: Iterable<Configurator>) {
        this.configurators.addAll(newConfigurators)
    }

    fun withMiddlewares(middlewares: Iterable<Middleware>) {
        this.store.addMiddlewares(middlewares)
    }

//    private var activityOperations: WeakReference<ActivityOperations> = WeakReference<ActivityOperations>(null)
//    val routingOperations: RoutingOperations = get()

    fun runOnUiThread(action: () -> Unit) {
        this.dispatchOnUiThread(action)
//        activityOperations.get()?.dispatchOnUiThread(action)
    }

    fun dispatch(action: ReduxAction) {
        this.store.dispatch(action)
    }

    fun showDialog(type: ActivityOperations.DialogType) {
//        activityOperations.get()?.showDialog(type)
    }

    fun showSnackbar(resId: Int) {
//        activityOperations.get()?.showSnackbar(resId)
    }

    fun subscribe(fragment: Fragment) {
        configurators.forEach { it.subscribe(fragment, fragment.hashCode()) }
    }

    fun unsubscribe(fragment: Fragment) {
        configurators.forEach { it.unsubscribe(fragment, fragment.hashCode()) }
    }

    fun bind(action: () -> ReduxAction) = Command {
        dispatch(action())
    }

    fun bind(action: ReduxAction) = Command {
        dispatch(action)
    }

    fun <T> bindWith(actionCreator: (T) -> ReduxAction) = Command.With<T> {
        dispatch(actionCreator(it))
    }
}

