package com.example.deliveryapp.screen.home.restaurant.detail.review

import android.widget.Toast
import androidx.core.os.bundleOf
import com.example.deliveryapp.databinding.FragmentListBinding
import com.example.deliveryapp.model.restaurant.RestaurantReviewModel
import com.example.deliveryapp.screen.base.BaseFragment
import com.example.deliveryapp.widget.adapter.ModelRecyclerAdapter
import com.example.deliveryapp.widget.adapter.listener.AdapterListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RestaurantReviewListFragment : BaseFragment<RestaurantReviewListViewModel, FragmentListBinding>() {

    override fun getViewBinding(): FragmentListBinding = FragmentListBinding.inflate(layoutInflater)

    override val viewModel by viewModel<RestaurantReviewListViewModel> {
        parametersOf(
            arguments?.getString(RESTAURANT_TITLE_KEY)
        )
    }

    private val adapter by lazy {
        ModelRecyclerAdapter<RestaurantReviewModel, RestaurantReviewListViewModel>(
            listOf(), viewModel, adapterListener = object : AdapterListener { }
        )
    }

    override fun initViews() {
        binding.recyclerVIew.adapter = adapter
    }

    override fun observeData() = viewModel.reviewStateLiveData.observe(viewLifecycleOwner) {
        when (it) {
            is RestaurantReviewState.Success -> {
                handleSuccess(it)
            }
        }
    }

    private fun handleSuccess(state: RestaurantReviewState.Success) {
        adapter.submitList(state.reviewList)
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
