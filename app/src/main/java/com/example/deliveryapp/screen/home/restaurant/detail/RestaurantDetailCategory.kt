package com.example.deliveryapp.screen.home.restaurant.detail

import androidx.annotation.StringRes
import com.example.deliveryapp.R

enum class RestaurantDetailCategory(
    @StringRes val categoryNameId: Int
) {

    MENU(R.string.menu), REVIEW(R.string.review)

}
