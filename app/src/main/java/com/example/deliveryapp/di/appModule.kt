package com.example.deliveryapp.di

import com.example.deliveryapp.util.provider.DefaultResourceProvider
import com.example.deliveryapp.util.provider.ResourcesProvider
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }
    single { provideRetrofit(get(), get()) }


    single<ResourcesProvider> { DefaultResourceProvider(androidApplication()) }

    single { Dispatchers.IO }
    single { Dispatchers.Main }
}