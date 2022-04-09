package io.gubarsergey.redux.configurators

import androidx.fragment.app.Fragment
import org.koin.core.component.KoinComponent

abstract class Configurator {
    abstract fun subscribe(fragment: Fragment, hashCode: Int)
    abstract fun unsubscribe(fragment: Fragment, hashCode: Int)
}
