package com.example.deliveryapp.di

import com.example.deliveryapp.data.entity.location.LocationLatLngEntity
import com.example.deliveryapp.data.entity.location.MapSearchInfoEntity
import com.example.deliveryapp.data.repository.map.DefaultMapRepository
import com.example.deliveryapp.data.repository.map.MapRepository
import com.example.deliveryapp.data.repository.restaurant.DefaultRestaurantRepository
import com.example.deliveryapp.data.repository.restaurant.RestaurantRepository
import com.example.deliveryapp.data.repository.user.DefaultUserRepository
import com.example.deliveryapp.data.repository.user.UserRepository
import com.example.deliveryapp.screen.MainViewModel
import com.example.deliveryapp.screen.home.HomeViewModel
import com.example.deliveryapp.screen.home.restaurant.RestaurantCategory
import com.example.deliveryapp.screen.home.restaurant.RestaurantListViewModel
import com.example.deliveryapp.screen.my.MyViewModel
import com.example.deliveryapp.screen.mylocation.MyLocationViewModel
import com.example.deliveryapp.util.event.MenuChangeEventBus
import com.example.deliveryapp.util.provider.DefaultResourceProvider
import com.example.deliveryapp.util.provider.ResourcesProvider
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    viewModel { MainViewModel() }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { MyViewModel() }
    viewModel { (restaurantCategory: RestaurantCategory, locationLatLngEntity: LocationLatLngEntity) -> RestaurantListViewModel(restaurantCategory, locationLatLngEntity, get()) }
    viewModel { (mapSearchInfoEntity: MapSearchInfoEntity) ->
        MyLocationViewModel(mapSearchInfoEntity, get(), get())
    }

    single<RestaurantRepository> { DefaultRestaurantRepository(get(), get(), get()) }
    single<MapRepository> { DefaultMapRepository(get(), get()) }
    single<UserRepository> { DefaultUserRepository(get(), get()) }

    // retrofit
    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }
    single { provideRetrofit(get(), get()) }
    single { provideMapApiService(get()) }

    single { provideDB(androidApplication()) }
    single { provideLocationDao(get()) }
    single<ResourcesProvider> { DefaultResourceProvider(androidApplication()) }

    single { MenuChangeEventBus() }

    single { Dispatchers.IO }
    single { Dispatchers.Main }
}