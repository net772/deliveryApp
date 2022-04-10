package com.example.deliveryapp.screen.home

import androidx.annotation.StringRes
import com.example.deliveryapp.data.entity.location.MapSearchInfoEntity

sealed class HomeState {

    object Uninitialized: HomeState()

    object Loading: HomeState()

    data class Success(
        val mapSearchInfoEntity: MapSearchInfoEntity
     //   val isLocationSame: Boolean,
      //  val foodMenuListInBasket: List<RestaurantFoodEntity>? = null
    ): HomeState()

    data class Error(
        @StringRes val messageId: Int
    ): HomeState()

}
