package com.example.deliveryapp.widget.adapter.listener.order

import com.example.deliveryapp.model.restaurant.FoodModel
import com.example.deliveryapp.widget.adapter.listener.AdapterListener

interface OrderMenuListListener: AdapterListener {

    fun onRemoveItem(model: FoodModel)

}
