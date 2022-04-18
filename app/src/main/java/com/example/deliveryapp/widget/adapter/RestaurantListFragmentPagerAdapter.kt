package com.example.deliveryapp.widget.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.deliveryapp.data.entity.location.LocationLatLngEntity
import com.example.deliveryapp.screen.home.restaurant.RestaurantListFragment

class RestaurantListFragmentPagerAdapter(
    fragment: Fragment,
    val fragmentList: List<RestaurantListFragment>,
    var locationLatLng: LocationLatLngEntity
    ) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}