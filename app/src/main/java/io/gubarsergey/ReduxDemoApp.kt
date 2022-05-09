package io.gubarsergey

import android.app.Application
import android.os.Handler
import android.os.Looper
import io.gubarsergey.artists.AvailableArtistsConfigurator
import io.gubarsergey.artists.AvailableArtistsConnector
import io.gubarsergey.artists.AvailableArtistsState
import io.gubarsergey.artists.availableArtists
import io.gubarsergey.artists.service.AvailableArtistsMiddleware
import io.gubarsergey.auth.AuthConfigurator
import io.gubarsergey.auth.AuthMiddleware
import io.gubarsergey.auth.AuthState
import io.gubarsergey.auth.authState
import io.gubarsergey.counter.CounterConfigurator
import io.gubarsergey.counter.IncrementCounterAction
import io.gubarsergey.di.artistsModule
import io.gubarsergey.di.authModule
import io.gubarsergey.di.networkModule
import io.gubarsergey.di.ordersModule
import io.gubarsergey.di.utilsModule
import io.gubarsergey.guards.OrderGuard
import io.gubarsergey.orders.OrdersConfigurator
import io.gubarsergey.orders.OrdersMiddleware
import io.gubarsergey.orders.OrdersState
import io.gubarsergey.orders.confirm.OrderConfirmationConfigurator
import io.gubarsergey.orders.confirm.OrderConfirmationMiddleware
import io.gubarsergey.orders.confirm.orderConfirmation
import io.gubarsergey.orders.create.CreateOrderConfigurator
import io.gubarsergey.orders.create.CreateOrderMiddleware
import io.gubarsergey.orders.create.CreateOrderState
import io.gubarsergey.orders.create.createOrder
import io.gubarsergey.orders.orders
import io.gubarsergey.orders.result.SendOrderResultConfigurator
import io.gubarsergey.orders.result.sendOrderResult
import io.gubarsergey.redux.Middleware
import io.gubarsergey.redux.ReduxCore
import io.gubarsergey.redux.redux.Reduce
import io.gubarsergey.redux.redux.ReduxAction
import io.gubarsergey.redux.setupRedux
import io.gubarsergey.settings.SettingsConfigurator
import io.gubarsergey.settings.SettingsMiddleware
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ReduxDemoApp : Application() {

    companion object {
        lateinit var core: ReduxCore<ReduxAppState>
    }

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate() {
        super.onCreate()

        timber()
        koin()
        redux()
    }

    private fun koin() {
        startKoin {
            androidContext(this@ReduxDemoApp)
            modules(
                listOf(
                    utilsModule,
                    networkModule,
                    authModule,
                    ordersModule,
                    artistsModule,
                )
            )
        }
    }

    private fun redux() {
        core = setupRedux(
            defaultState = ReduxAppState.default,
            applyReducers = { state, action ->
                ReduxAppState(
                    auth = Reduce.authState(state.auth, action),
                    myOrders = Reduce.orders(state.myOrders, action),
                    availableArtists = Reduce.availableArtists(state.availableArtists, action),
                    createOrder = Reduce.createOrder(state.createOrder, action),
                    orderConfirmation = Reduce.orderConfirmation(state.orderConfirmation, action),
                    sendOrderResultState = Reduce.sendOrderResult(state.sendOrderResultState, action),
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
                    AvailableArtistsMiddleware(this),
                    CreateOrderMiddleware(this),
                    OrderGuard(this),
                    OrderConfirmationMiddleware(this),
                    SettingsMiddleware(),
                )
            )
            withConfigurators(
                listOf(
                    CounterConfigurator(this),
                    AuthConfigurator(this),
                    OrdersConfigurator(this),
                    AvailableArtistsConfigurator(this),
                    CreateOrderConfigurator(this),
                    OrderConfirmationConfigurator(this),
                    SendOrderResultConfigurator(this),
                    SettingsConfigurator(this),
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


