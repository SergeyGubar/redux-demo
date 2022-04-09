package io.gubarsergey.redux

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import io.gubarsergey.redux.redux.ReduxAction

fun <AppState> Application.setupRedux(
    defaultState: AppState,
    applyReducers: (AppState, ReduxAction) -> AppState,
    runOnUiThread: (() -> Unit) -> Unit,
    block: ReduxCore<AppState>.() -> Unit
): ReduxCore<AppState> {

    val core = ReduxCore(defaultState, applyReducers, runOnUiThread)

    this.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            if (activity is AppCompatActivity) {
                activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
                            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
                            core.subscribe(f)
                        }

                        override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                            super.onFragmentViewDestroyed(fm, f)
                            core.unsubscribe(f)
                        }
                    },
                    true
                )
            }
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
        }
    })

    core.block()

    return core
}
