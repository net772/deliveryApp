package com.example.deliveryapp.widget.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class RestaurantDetailListFragmentPagerAdapter(
    activity: FragmentActivity,
    val fragmentList: List<Fragment>
) : FragmentStateAdapter(activity) {

    init {
        Log.d("동현"," : $")
    }
    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}
