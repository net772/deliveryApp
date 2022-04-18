package com.example.deliveryapp.data.repository.restaurant

import com.example.deliveryapp.data.entity.location.LocationLatLngEntity
import com.example.deliveryapp.data.entity.restaurant.RestaurantEntity
import com.example.deliveryapp.screen.home.restaurant.RestaurantCategory

interface RestaurantRepository {

    suspend fun getList(
        restaurantCategory: RestaurantCategory,
        locationLatLngEntity: LocationLatLngEntity
    ): List<RestaurantEntity>
}