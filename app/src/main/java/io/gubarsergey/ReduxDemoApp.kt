package io.gubarsergey

import android.app.Application
import android.os.Handler
import android.os.Looper
import io.gubarsergey.auth.AuthConfigurator
import io.gubarsergey.auth.AuthMiddleware
import io.gubarsergey.auth.AuthState
import io.gubarsergey.auth.authState
import io.gubarsergey.counter.CounterConfigurator
import io.gubarsergey.counter.IncrementCounterAction
import io.gubarsergey.di.authModule
import io.gubarsergey.di.networkModule
import io.gubarsergey.redux.Middleware
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.redux.Reduce
import io.gubarsergey.redux.redux.ReduxAction
import io.gubarsergey.redux.setupRedux
import org.koin.core.context.startKoin
import timber.log.Timber

class ReduxDemoApp : Application() {

    private lateinit var core: ReduxCore<ReduxAppState>
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate() {
        super.onCreate()

        timber()
        koin()
        redux()
    }

    private fun koin() {
        startKoin {
            modules(
                listOf(
                    networkModule,
                    authModule
                )
            )
        }
    }

    private fun redux() {
        core = setupRedux(
            defaultState = ReduxAppState(auth = AuthState.default),
            applyReducers = { state, action ->
                state.copy(
                    auth = Reduce.authState(state.auth, action)
                )
            },
            runOnUiThread = { action ->
                handler.post(action)
            }
        ) {
            withMiddlewares(
                listOf(
                    LoggingMiddleware,
                    AnalyticsMiddleware,
                    AuthMiddleware(this)
                )
            )
            withConfigurators(
                listOf(
                    CounterConfigurator(this),
                    AuthConfigurator(this)
                )
            )
        }
    }

    private fun timber() {
        Timber.plant(Timber.DebugTree())
    }
}

object LoggingMiddleware : Middleware {
    override fun apply(action: ReduxAction) {
        Timber.d("action $action")
    }
}

object AnalyticsMiddleware : Middleware {
    override fun apply(action: ReduxAction) {
    }
}


