package com.example.deliveryapp.screen.home.restaurant.detail.menu

import android.os.Bundle
import android.util.Log
import com.example.deliveryapp.data.entity.restaurant.RestaurantFoodEntity
import com.example.deliveryapp.databinding.FragmentListBinding
import com.example.deliveryapp.model.restaurant.FoodModel
import com.example.deliveryapp.screen.base.BaseFragment
import com.example.deliveryapp.screen.home.restaurant.detail.RestaurantDetailViewModel
import com.example.deliveryapp.widget.adapter.ModelRecyclerAdapter
import com.example.deliveryapp.widget.adapter.listener.restaurant.FoodMenuListListener
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RestaurantMenuListFragment : BaseFragment<RestaurantMenuListViewModel, FragmentListBinding>() {

    override fun getViewBinding(): FragmentListBinding = FragmentListBinding.inflate(layoutInflater)

    private val restaurantId by lazy { arguments?.getLong(RESTAURANT_ID_KEY, -1) }

    private val restaurantFoodList by lazy { arguments?.getParcelableArrayList<RestaurantFoodEntity>(
        FOOD_LIST_KEY)!! }

    override val viewModel by viewModel<RestaurantMenuListViewModel> { parametersOf(restaurantId, restaurantFoodList) }

    private val restaurantDetailViewModel by sharedViewModel<RestaurantDetailViewModel>()

    private val adapter by lazy {
        ModelRecyclerAdapter<FoodModel, RestaurantMenuListViewModel>(listOf(), viewModel, adapterListener = object : FoodMenuListListener {
            override fun onClickItem(model: FoodModel) {
                //viewModel.insertMenuInBasket(model)
            }
        })
    }

    override fun initViews() = with(binding) {
        Log.d("동현"," : $restaurantFoodList")
        recyclerVIew.adapter = adapter
    }

    override fun observeData() {
        viewModel.restaurantMenuListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    companion object {
        const val RESTAURANT_ID_KEY = "restaurantId"
        const val FOOD_LIST_KEY = "foodList"

        fun newInstance(restaurantId: Long, foodList: ArrayList<RestaurantFoodEntity>): RestaurantMenuListFragment {
            val bundle = Bundle().apply {
                putLong(RESTAURANT_ID_KEY, restaurantId)
                putParcelableArrayList(FOOD_LIST_KEY, foodList)
            }

            return RestaurantMenuListFragment().apply {
                arguments = bundle
            }
        }

    }
}
