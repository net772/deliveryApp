package com.example.deliveryapp.data.repository

import com.example.deliveryapp.data.entity.restaurant.RestaurantEntity
import com.example.deliveryapp.screen.home.restaurant.RestaurantCategory

interface RestaurantRepository {

    suspend fun getList(
        restaurantCategory: RestaurantCategory
    ): List<RestaurantEntity>
}