package io.gubarsergey.redux.redux

import kotlin.reflect.KProperty

object Reduce

class Reducer<State>(val reduce: (State, ReduxAction) -> State) {
    operator fun invoke(state: State, action: ReduxAction) = reduce(state, action)

    operator fun getValue(host: Reduce, property: KProperty<*>) = this
}
