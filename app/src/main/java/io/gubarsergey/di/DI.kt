package io.gubarsergey.di

import io.gubarsergey.auth.service.AuthAPI
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5486")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}

val authModule = module {
    single<AuthAPI> {
        get<Retrofit>().create(AuthAPI::class.java)
    }
}
