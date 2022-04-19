package com.example.deliveryapp.screen.home.restaurant.detail.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.data.entity.restaurant.RestaurantFoodEntity
import com.example.deliveryapp.model.restaurant.FoodModel
import com.example.deliveryapp.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RestaurantMenuListViewModel(
    private val restaurantId: Long,
    private val foodEntityList: List<RestaurantFoodEntity>
) : BaseViewModel() {

    val restaurantMenuListLiveData = MutableLiveData<List<FoodModel>>()

    override fun fetchData(): Job = viewModelScope.launch {
        restaurantMenuListLiveData.value = foodEntityList.map {
            FoodModel(
                id = it.hashCode().toLong(),
                title = it.title,
                description = it.description,
                price = it.price,
                imageUrl = it.imageUrl,
                restaurantId = restaurantId,
                foodId = it.id,
                restaurantTitle = it.restaurantTitle
            )
        }
    }

}
