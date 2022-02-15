package com.example.deliveryapp.di

import com.example.deliveryapp.data.repository.DefaultRestaurantRepository
import com.example.deliveryapp.data.repository.RestaurantRepository
import com.example.deliveryapp.screen.home.HomeViewModel
import com.example.deliveryapp.screen.home.restaurant.RestaurantCategory
import com.example.deliveryapp.screen.home.restaurant.RestaurantListViewModel
import com.example.deliveryapp.screen.my.MyViewModel
import com.example.deliveryapp.util.provider.DefaultResourceProvider
import com.example.deliveryapp.util.provider.ResourcesProvider
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { HomeViewModel() }
    viewModel { MyViewModel() }
    viewModel { (restaurantCategory: RestaurantCategory) -> RestaurantListViewModel(restaurantCategory, get()) }

    single<RestaurantRepository> { DefaultRestaurantRepository(get(), get()) }

    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }

    single { provideRetrofit(get(), get()) }

    single<ResourcesProvider> { DefaultResourceProvider(androidApplication()) }

    single { Dispatchers.IO }
    single { Dispatchers.Main }
}