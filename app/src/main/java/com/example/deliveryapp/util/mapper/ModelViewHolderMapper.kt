
package com.example.deliveryapp.util.mapper

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import com.example.deliveryapp.databinding.ViewholderEmptyBinding
import com.example.deliveryapp.model.CellType
import com.example.deliveryapp.model.Model
import com.example.deliveryapp.screen.base.BaseViewModel
import com.example.deliveryapp.util.provider.ResourcesProvider
import com.example.deliveryapp.widget.adapter.viewholder.EmptyViewHolder
import com.example.deliveryapp.widget.adapter.viewholder.ModelViewHolder

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
            CellType.EMPTY_CELL -> EmptyViewHolder(
                ViewholderEmptyBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )

            else -> Unit
        }
        return viewHolder as ModelViewHolder<M>
    }
}