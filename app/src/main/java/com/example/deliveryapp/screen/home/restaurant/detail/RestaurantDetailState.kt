package com.example.deliveryapp.screen.home.restaurant.detail

import com.example.deliveryapp.data.entity.restaurant.RestaurantEntity
import com.example.deliveryapp.data.entity.restaurant.RestaurantFoodEntity

sealed class RestaurantDetailState {

    object Uninitialized: RestaurantDetailState()

    object Loading: RestaurantDetailState()

    data class Success(
        val restaurantEntity: RestaurantEntity,
        val restaurantFoodList: List<RestaurantFoodEntity>? = null,
        val isLiked: Boolean? = null
    ): RestaurantDetailState()

}
