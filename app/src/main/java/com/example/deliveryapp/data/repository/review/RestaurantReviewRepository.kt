package com.example.deliveryapp.data.repository.review

import com.example.deliveryapp.data.entity.restaurant.RestaurantReviewEntity

interface RestaurantReviewRepository {

    suspend fun getReviews(restaurantTitle: String): List<RestaurantReviewEntity>

}
