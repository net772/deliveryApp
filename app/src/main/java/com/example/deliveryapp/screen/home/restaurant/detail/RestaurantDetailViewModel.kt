package com.example.deliveryapp.screen.home.restaurant.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.data.entity.restaurant.RestaurantEntity
import com.example.deliveryapp.data.entity.restaurant.RestaurantFoodEntity
import com.example.deliveryapp.data.repository.restaurant.food.RestaurantFoodRepository
import com.example.deliveryapp.data.repository.user.UserRepository
import com.example.deliveryapp.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RestaurantDetailViewModel(
    private val restaurantEntity: RestaurantEntity,
    private val restaurantFoodRepository: RestaurantFoodRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {
    val restaurantDetailStateLiveData = MutableLiveData<RestaurantDetailState>(RestaurantDetailState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch {
//        restaurantDetailStateLiveData.value = RestaurantDetailState.Success(
//            restaurantEntity = restaurantEntity
//        )
        restaurantDetailStateLiveData.value = RestaurantDetailState.Loading

        val foods = restaurantFoodRepository.getFoods(restaurantEntity.restaurantInfoId, restaurantEntity.restaurantTitle)
        val foodMenuListInBasket = restaurantFoodRepository.getAllFoodMenuListInBasket()
        val isLiked = userRepository.getUserLikedRestaurant(restaurantEntity.restaurantTitle) != null
        restaurantDetailStateLiveData.value = RestaurantDetailState.Success(
            restaurantEntity = restaurantEntity,
            restaurantFoodList = foods,
            foodMenuListInBasket = foodMenuListInBasket,
            isLiked = isLiked
        )
    }

    fun toggleLikedRestaurant() = viewModelScope.launch {
        when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                userRepository.getUserLikedRestaurant(restaurantEntity.restaurantTitle)?.let {
                    userRepository.deleteUserLikedRestaurant(it.restaurantTitle)
                    restaurantDetailStateLiveData.value = data.copy(
                        isLiked = false
                    )
                } ?: kotlin.run {
                    userRepository.insertUserLikedRestaurant(restaurantEntity)
                    restaurantDetailStateLiveData.value = data.copy(
                        isLiked = true
                    )
                }
            }
            else -> Unit
        }
    }

    fun getRestaurantPhoneNumber(): String? {
        return when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                data.restaurantEntity.restaurantTelNumber
            }
            else -> null
        }
    }

    fun getRestaurantInfo(): RestaurantEntity? {
        return when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                data.restaurantEntity
            }
            else -> null
        }
    }

    fun notifyFoodMenuListInBasket(foodMenu: RestaurantFoodEntity) = viewModelScope.launch {
        when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                restaurantDetailStateLiveData.value = data.copy(
                    foodMenuListInBasket = data.foodMenuListInBasket?.toMutableList()?.apply {
                        add(foodMenu)
                    }
                )
            }
            else -> Unit
        }
    }

    fun notifyClearNeedAlertInBasket(isClearNeed: Boolean, afterAction: () -> Unit) = viewModelScope.launch {
        when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                restaurantDetailStateLiveData.value = data.copy(
                    isClearNeedInBasketAndAction = Pair(isClearNeed, afterAction)
                )
            }
            else -> Unit
        }
    }

    fun notifyClearBasket() = viewModelScope.launch {
        when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                restaurantDetailStateLiveData.value = data.copy(
                    foodMenuListInBasket = listOf(),
                    isClearNeedInBasketAndAction = Pair(false, {})
                )
            }
            else -> Unit
        }
    }

    fun checkMyBasket() = viewModelScope.launch {
        when (val data = restaurantDetailStateLiveData.value) {
            is RestaurantDetailState.Success -> {
                restaurantDetailStateLiveData.value = data.copy(
                    foodMenuListInBasket = restaurantFoodRepository.getAllFoodMenuListInBasket()
                )
            }
            else -> Unit
        }
    }
}