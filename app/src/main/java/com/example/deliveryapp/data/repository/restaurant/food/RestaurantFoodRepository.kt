package com.example.deliveryapp.data.repository.restaurant.food

import com.example.deliveryapp.data.entity.restaurant.RestaurantFoodEntity


interface RestaurantFoodRepository {
    suspend fun getFoods(restaurantId: Long, restaurantTitle: String): List<RestaurantFoodEntity>
    suspend fun getFoodMenuListInBasket(restaurantId: Long): List<RestaurantFoodEntity>
    suspend fun getAllFoodMenuListInBasket(): List<RestaurantFoodEntity>
    suspend fun insertFoodMenuInBasket(restaurantFoodEntity: RestaurantFoodEntity)
    suspend fun removeFoodMenuListInBasket(foodId: String)
    suspend fun clearFoodMenuListInBasket()
}
