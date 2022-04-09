package io.gubarsergey

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

abstract class BaseConnector<Props> : CoroutineScope {

    override val coroutineContext: CoroutineContext get() = Dispatchers.Main

    abstract fun map(appState: ReduxAppState): Props
}

val <Props> BaseConnector<Props>.defaultTag get() = this::class.java.simpleName
