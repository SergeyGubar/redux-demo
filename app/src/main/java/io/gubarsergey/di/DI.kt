package io.gubarsergey.di

import io.gubarsergey.auth.service.AuthAPI
import io.gubarsergey.orders.service.OrdersAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

val authModule = module {
    single<AuthAPI> {
        get<Retrofit>().create(AuthAPI::class.java)
    }
}

val ordersModule = module {
    single<OrdersAPI> {
        get<Retrofit>().create(OrdersAPI::class.java)
    }
}
