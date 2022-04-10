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
import io.gubarsergey.di.ordersModule
import io.gubarsergey.orders.OrdersConfigurator
import io.gubarsergey.orders.OrdersMiddleware
import io.gubarsergey.orders.OrdersState
import io.gubarsergey.orders.orders
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
                    authModule,
                    ordersModule
                )
            )
        }
    }

    private fun redux() {
        core = setupRedux(
            defaultState = ReduxAppState(
                auth = AuthState.default,
                myOrders = OrdersState.default,
            ),
            applyReducers = { state, action ->
                ReduxAppState(
                    auth = Reduce.authState(state.auth, action),
                    myOrders = Reduce.orders(state.myOrders, action),
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
                    AuthMiddleware(this),
                    OrdersMiddleware(this),
                )
            )
            withConfigurators(
                listOf(
                    CounterConfigurator(this),
                    AuthConfigurator(this),
                    OrdersConfigurator(this),
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


