@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.example.deliveryapp.util.mapper

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.deliveryapp.databinding.*
import com.example.deliveryapp.model.CellType
import com.example.deliveryapp.model.Model
import com.example.deliveryapp.screen.base.BaseViewModel
import com.example.deliveryapp.util.provider.ResourcesProvider
import com.example.deliveryapp.widget.adapter.viewholder.restaurant.RestaurantViewHolder
import com.example.deliveryapp.widget.adapter.viewholder.ModelViewHolder
import com.example.deliveryapp.widget.adapter.viewholder.food.FoodMenuViewHolder
import com.example.deliveryapp.widget.adapter.viewholder.order.OrderMenuViewHolder
import com.example.deliveryapp.widget.adapter.viewholder.restaurant.LikeRestaurantViewHolder
import com.example.deliveryapp.widget.adapter.viewholder.review.RestaurantReviewViewHolder

object ModelViewHolderMapper {

    @Suppress("UNCHECKED_CAST")
    fun <M: Model> map(
        parent: ViewGroup,
        type: CellType,
        viewModel: BaseViewModel,
        resourcesProvider: ResourcesProvider
    ) : ModelViewHolder<M> {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = when (type) {
            CellType.LIKE_RESTAURANT_CELL ->
                LikeRestaurantViewHolder(
                    ViewholderLikeRestaurantBinding.inflate(inflater, parent, false),
                    viewModel,
                    resourcesProvider
                )

            CellType.REVIEW_CELL -> {
                RestaurantReviewViewHolder(
                    ViewholderRestaurantReviewBinding.inflate(inflater, parent, false),
                    viewModel,
                    resourcesProvider
                )
            }

            CellType.RESTAURANT_CELL -> RestaurantViewHolder(
                ViewholderRestaurantBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )

            CellType.FOOD_CELL ->
                FoodMenuViewHolder(
                    ViewholderFoodMenuBinding.inflate(inflater, parent, false),
                    viewModel,
                    resourcesProvider
                )

            CellType.ORDER_FOOD_CELL ->
                OrderMenuViewHolder(
                    ViewholderOrderMenuBinding.inflate(inflater, parent, false),
                    viewModel,
                    resourcesProvider
                )

            else -> Unit
        }
        return viewHolder as ModelViewHolder<M>
    }
}