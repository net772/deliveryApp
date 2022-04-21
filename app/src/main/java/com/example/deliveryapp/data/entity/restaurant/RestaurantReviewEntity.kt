package com.example.deliveryapp.data.entity.restaurant

import android.net.Uri
import com.example.deliveryapp.data.entity.Entity

data class RestaurantReviewEntity(
    override val id: Long,
    val title: String,
    val description: String,
    val grade: Float,
    val images: List<Uri>? = null
): Entity
