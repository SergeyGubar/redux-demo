package io.gubarsergey.redux

import io.gubarsergey.redux.redux.ReduxAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import kotlin.coroutines.CoroutineContext

interface Middleware : CoroutineScope, KoinComponent {
    fun apply(action: ReduxAction)
    override val coroutineContext: CoroutineContext get() = Dispatchers.Main
}
