package com.example.deliveryapp.data.repository.user

import com.example.deliveryapp.data.entity.location.LocationLatLngEntity
import com.example.deliveryapp.data.entity.restaurant.RestaurantEntity

interface UserRepository {

    suspend fun getUserLocation(): LocationLatLngEntity?

    suspend fun insertUserLocation(locationLatLngEntity: LocationLatLngEntity)

    suspend fun getUserLikedRestaurant(restaurantTitle: String): RestaurantEntity?

    suspend fun deleteUserLikedRestaurant(restaurantTitle: String)

    suspend fun insertUserLikedRestaurant(restaurantEntity: RestaurantEntity)
}
