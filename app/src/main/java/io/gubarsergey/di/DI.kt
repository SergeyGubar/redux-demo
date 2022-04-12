package io.gubarsergey.di

import io.gubarsergey.artists.service.AvailableArtistsAPI
import io.gubarsergey.auth.mvvm.AuthViewModel
import io.gubarsergey.auth.mvvm.PrefHelper
import io.gubarsergey.auth.mvvm.SharedPrefHelper
import io.gubarsergey.auth.service.AuthAPI
import io.gubarsergey.orders.service.OrdersAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val okHttp = OkHttpClient.Builder().addInterceptor(interceptor).build()

        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5486")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttp)
            .build()
    }
}

val utilsModule = module {
    single<PrefHelper> {
        SharedPrefHelper(androidContext())
    }
}

val authModule = module {
    single<AuthAPI> {
        get<Retrofit>().create(AuthAPI::class.java)
    }

    viewModel {
        AuthViewModel(get(), get())
    }
}

val ordersModule = module {
    single<OrdersAPI> {
        get<Retrofit>().create(OrdersAPI::class.java)
    }
}

val artistsModule = module {
    single<AvailableArtistsAPI> {
        get<Retrofit>().create(AvailableArtistsAPI::class.java)
    }
}
