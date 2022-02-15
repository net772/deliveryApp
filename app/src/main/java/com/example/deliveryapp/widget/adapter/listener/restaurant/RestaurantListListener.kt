package com.example.deliveryapp.widget.adapter.listener.restaurant

import com.example.deliveryapp.model.restaurant.RestaurantModel
import com.example.deliveryapp.widget.adapter.listener.AdapterListener

interface RestaurantListListener: AdapterListener {

    fun onClickItem(model: RestaurantModel)
}