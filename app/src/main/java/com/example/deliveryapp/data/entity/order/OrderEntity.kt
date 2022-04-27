package com.example.deliveryapp.data.entity.order

import com.example.deliveryapp.data.entity.restaurant.RestaurantFoodEntity

data class OrderEntity(
    val id: String,
    val userId: String,
    val restaurantId: Long,
    val foodMenuList: List<RestaurantFoodEntity>,
    val restaurantTitle: String
)
