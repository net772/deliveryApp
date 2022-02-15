package com.example.deliveryapp.screen.home

import android.util.Log
import com.example.deliveryapp.databinding.FragmentHomeBinding
import com.example.deliveryapp.screen.base.BaseFragment
import com.example.deliveryapp.screen.home.restaurant.RestaurantCategory
import com.example.deliveryapp.screen.home.restaurant.RestaurantListFragment
import com.example.deliveryapp.widget.adapter.RestaurantListFragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment: BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    companion object {
        const val TAG = "HomeFragment"
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewPagerAdapter: RestaurantListFragmentPagerAdapter

    override val viewModel by viewModel<HomeViewModel>()

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun observeData() = Unit

    override fun initViews() {
        super.initViews()
        initViewPager()
    }

    private fun initViewPager() = with(binding) {
        val restaurantCategories = RestaurantCategory.values()

        if(::viewPagerAdapter.isInitialized.not()) {
            val restaurantListFragmentList = restaurantCategories.map {
                RestaurantListFragment.newInstance(it)
            }

            viewPagerAdapter = RestaurantListFragmentPagerAdapter(
                this@HomeFragment,
                restaurantListFragmentList,
            )
            viewPager.adapter = viewPagerAdapter
        }
        viewPager.offscreenPageLimit = restaurantCategories.size  // 한번 만들어진 프래그먼트를 계속 쓸 수 있도록 처리
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setText(restaurantCategories[position].categoryNameId)
        }.attach()

    }

}