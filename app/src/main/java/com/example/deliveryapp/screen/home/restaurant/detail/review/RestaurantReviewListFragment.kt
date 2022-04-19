package com.example.deliveryapp.screen.home.restaurant.detail.review

import androidx.core.os.bundleOf
import com.example.deliveryapp.databinding.FragmentListBinding
import com.example.deliveryapp.screen.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RestaurantReviewListFragment : BaseFragment<RestaurantReviewListViewModel, FragmentListBinding>() {

    override fun getViewBinding(): FragmentListBinding = FragmentListBinding.inflate(layoutInflater)

    override val viewModel by viewModel<RestaurantReviewListViewModel> ()
    override fun observeData() {

    }

    companion object {

        const val RESTAURANT_TITLE_KEY = "restaurantTitle"

        fun newInstance(restaurantTitle: String): RestaurantReviewListFragment {
            val bundle = bundleOf(
                RESTAURANT_TITLE_KEY to restaurantTitle
            )
            return RestaurantReviewListFragment().apply {
                arguments = bundle
            }
        }

    }

}
