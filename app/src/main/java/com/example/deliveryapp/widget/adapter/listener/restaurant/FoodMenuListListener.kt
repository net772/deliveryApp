package com.example.deliveryapp.widget.adapter.listener.restaurant

import com.example.deliveryapp.model.restaurant.FoodModel
import com.example.deliveryapp.widget.adapter.listener.AdapterListener

interface FoodMenuListListener: AdapterListener {

    fun onClickItem(model: FoodModel)

}
