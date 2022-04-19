package com.example.deliveryapp.screen.home.restaurant

import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import com.example.deliveryapp.data.entity.location.LocationLatLngEntity
import com.example.deliveryapp.databinding.FragmentListBinding
import com.example.deliveryapp.model.restaurant.RestaurantModel
import com.example.deliveryapp.screen.base.BaseFragment
import com.example.deliveryapp.screen.home.restaurant.detail.RestaurantDetailActivity
import com.example.deliveryapp.widget.adapter.ModelRecyclerAdapter
import com.example.deliveryapp.widget.adapter.listener.restaurant.RestaurantListListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RestaurantListFragment: BaseFragment<RestaurantListViewModel, FragmentListBinding>() {
    private val restaurantCategory by lazy { arguments?.getSerializable(RESTAURANT_CATEGORY_KEY) as RestaurantCategory }
    private val locationLatLngEntity by lazy<LocationLatLngEntity> { arguments?.getParcelable(LOCATION_KEY)!! }

    override val viewModel by viewModel<RestaurantListViewModel>() { parametersOf(restaurantCategory, locationLatLngEntity) }

    override fun getViewBinding(): FragmentListBinding = FragmentListBinding.inflate(layoutInflater)

    private val adapter by lazy {
        ModelRecyclerAdapter<RestaurantModel, RestaurantListViewModel>(listOf(), viewModel, adapterListener = object : RestaurantListListener {
            override fun onClickItem(model: RestaurantModel) {
                startActivity(
                    RestaurantDetailActivity.newIntent(requireContext(), model.toEntity())
                )
            }
        })
    }

    override fun initViews() = with(binding) {
        recyclerVIew.adapter = adapter
    }

    override fun observeData() = viewModel.restaurantListLiveData.observe(viewLifecycleOwner) {
        adapter.submitList(it)
    }

    companion object {
        const val RESTAURANT_KEY = "Restaurant"
        const val RESTAURANT_CATEGORY_KEY = "restaurantCategory"
        const val LOCATION_KEY = "location"

        fun newInstance(restaurantCategory: RestaurantCategory, locationLatLng: LocationLatLngEntity): RestaurantListFragment {
            return RestaurantListFragment().apply {
                arguments = bundleOf(
                    RESTAURANT_CATEGORY_KEY to restaurantCategory, // ex) -> (restaurantCategory, ALL)
                    LOCATION_KEY to locationLatLng
                )
            }
        }
    }
}