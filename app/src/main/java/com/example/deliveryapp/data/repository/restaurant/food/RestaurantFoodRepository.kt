package com.example.deliveryapp.data.repository.restaurant.food

import com.example.deliveryapp.data.entity.restaurant.RestaurantFoodEntity


interface RestaurantFoodRepository {

    suspend fun getFoods(restaurantId: Long, restaurantTitle: String): List<RestaurantFoodEntity>

}
