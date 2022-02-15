package com.example.deliveryapp.screen.my

import android.util.Log
import com.example.deliveryapp.databinding.FragmentHomeBinding
import com.example.deliveryapp.databinding.FragmentMyBinding
import com.example.deliveryapp.screen.base.BaseFragment
import com.example.deliveryapp.screen.home.HomeFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MyFragment: BaseFragment<MyViewModel, FragmentMyBinding>() {
    companion object {
        const val TAG = "MyFragment"
        fun newInstance() = MyFragment()
    }


    override val viewModel by viewModel<MyViewModel>()

    override fun getViewBinding(): FragmentMyBinding = FragmentMyBinding.inflate(layoutInflater)

    override fun observeData() = Unit

    override fun initViews() {
        super.initViews()
    }
}