package com.example.deliveryapp.data.repository.order

import com.example.deliveryapp.data.entity.restaurant.RestaurantFoodEntity

interface OrderRepository {

    suspend fun orderMenu(
        userId: String,
        restaurantId: Long,
        foodMenuList: List<RestaurantFoodEntity>,
        restaurantTitle: String
    ): DefaultOrderRepository.Result

    suspend fun getAllOrderMenus(
        userId: String
    ): DefaultOrderRepository.Result

}
