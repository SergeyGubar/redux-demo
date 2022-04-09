package io.gubarsergey.redux.redux

class Observer<State>(private val observe: (State) -> Unit, val tag: String, val hashCode: Int) {

    operator fun invoke(state: State) {
        observe(state)
    }
}
