package com.example.deliveryapp.screen.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.deliveryapp.R
import com.example.deliveryapp.data.entity.location.LocationLatLngEntity
import com.example.deliveryapp.data.entity.location.MapSearchInfoEntity
import com.example.deliveryapp.databinding.FragmentHomeBinding
import com.example.deliveryapp.screen.MainActivity
import com.example.deliveryapp.screen.MainTabMenu
import com.example.deliveryapp.screen.base.BaseFragment
import com.example.deliveryapp.screen.home.HomeViewModel.Companion.MY_LOCATION_KEY
import com.example.deliveryapp.screen.home.restaurant.RestaurantCategory
import com.example.deliveryapp.screen.home.restaurant.RestaurantListFragment
import com.example.deliveryapp.screen.home.restaurant.RestautantFilterOrder
import com.example.deliveryapp.screen.mylocation.MyLocationActivity
import com.example.deliveryapp.screen.order.OrderMenuListActivity
import com.example.deliveryapp.widget.adapter.RestaurantListFragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment: BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    companion object {
        const val TAG = "HomeFragment"
        fun newInstance() = HomeFragment()

        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private val firebaseAuth by lazy { FirebaseAuth.getInstance()}
    private lateinit var viewPagerAdapter: RestaurantListFragmentPagerAdapter

    override val viewModel by viewModel<HomeViewModel>()

    private lateinit var locationManager: LocationManager

    private lateinit var myLocationListener: MyLocationListener

    private val changeLocationLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.getParcelableExtra<MapSearchInfoEntity>(MY_LOCATION_KEY)?.let { myLocationInfo ->
                viewModel.loadReverseGeoInformation(myLocationInfo.locationLatLng)
            }
        }
    }

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val responsePermissions = permissions.entries.filter {
                it.key == Manifest.permission.ACCESS_FINE_LOCATION || it.key == Manifest.permission.ACCESS_COARSE_LOCATION
            }
            if (responsePermissions.filter { it.value == true }.size == locationPermissions.size) {
                setMyLocationListener()
            } else {
                with(binding.locationTitleTextView) {
                    text = getString(R.string.please_request_location_permission)
                    setOnClickListener {
                        getMyLocation()
                    }
                }
                Toast.makeText(requireContext(), getString(R.string.can_not_assigned_permission), Toast.LENGTH_SHORT).show()
            }
        }
    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun onResume() {
        super.onResume()
        viewModel.checkMyBasket()
    }

    override fun initViews() = with(binding) {
        locationTitleTextView.setOnClickListener {
            viewModel.getMapSearchInfo()?.let { mapInfo ->
                changeLocationLauncher.launch(
                    MyLocationActivity.newIntent(
                        requireContext(), mapInfo
                    )
                )
            }
        }

        orderChipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipDefault -> {
                    chipInitialize.isGone = true
                    changeRestaurantFilterOrder(RestautantFilterOrder.DEFAULT)
                }
                R.id.chipInitialize -> {
                    chipDefault.isChecked = true
                }
                R.id.chipDeliveryTip -> {
                    chipInitialize.isVisible = true
                    changeRestaurantFilterOrder(RestautantFilterOrder.LOW_DELIVERY_TIP)
                }
                R.id.chipFastDelivery -> {
                    chipInitialize.isVisible = true
                    changeRestaurantFilterOrder(RestautantFilterOrder.FAST_DELIVERY)
                }
                R.id.chipTopRate -> {
                    chipInitialize.isVisible = true
                    changeRestaurantFilterOrder(RestautantFilterOrder.TOP_RATE)
                }
            }
        }
    }

    private fun changeRestaurantFilterOrder(order: RestautantFilterOrder) {
        viewPagerAdapter.fragmentList.forEach {
            it.viewModel.setRestaurantFilterOrder(order)
        }
    }

    override fun observeData() {
        viewModel.homeStateLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is HomeState.Uninitialized -> {
                    getMyLocation()
                }
                is HomeState.Loading -> {
                    binding.locationLoading.isVisible = true
                    binding.locationTitleTextView.text = getString(R.string.loading)
                }
                is HomeState.Success -> {
                    binding.locationLoading.isGone = true
                    binding.tabLayout.isVisible = true
                    binding.filterScrollView.isVisible = true
                    binding.viewPager.isVisible = true
                    binding.locationTitleTextView.text = it.mapSearchInfoEntity.fullAddress
                    initViewPager(it.mapSearchInfoEntity.locationLatLng)
                    if (it.isLocationSame.not()) {
                        Toast.makeText(requireContext(), R.string.please_set_your_current_location, Toast.LENGTH_SHORT).show()
                    }
                }
                is HomeState.Error -> {
                    binding.locationLoading.isGone = true
                    binding.locationTitleTextView.setText(R.string.location_not_found)
                    binding.locationTitleTextView.setOnClickListener {
                        getMyLocation()
                    }
                    Toast.makeText(requireContext(), it.messageId, Toast.LENGTH_SHORT).show()
                }
                else -> Unit
            }
        }

        viewModel.foodMenuBasketLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.basketButtonContainer.isVisible = true
                binding.basketCountTextView.text = getString(R.string.basket_count, it.size)
                binding.basketButton.setOnClickListener {
                    if (firebaseAuth.currentUser == null) {
                        alertLoginNeed {
                            (requireActivity() as MainActivity).goToTab(MainTabMenu.MY)
                        }
                    } else {
                        startActivity(
                            OrderMenuListActivity.newIntent(requireActivity())
                        )
                    }
                }

            } else {
                binding.basketButtonContainer.isGone = true
                binding.basketButton.setOnClickListener(null)
            }
        }
    }

    private fun alertLoginNeed(afterAction: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle("???????????? ???????????????.")
            .setMessage("??????????????? ???????????? ???????????????. My????????? ?????????????????????????")
            .setPositiveButton("??????") { dialog, _ ->
                afterAction()
                dialog.dismiss()
            }
            .setNegativeButton("??????") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }



    private fun initViewPager(locationLatLng: LocationLatLngEntity) = with(binding) {
        val restaurantCategories = RestaurantCategory.values()

        if(::viewPagerAdapter.isInitialized.not()) {
            val restaurantListFragmentList = restaurantCategories.map {
                RestaurantListFragment.newInstance(it, locationLatLng)
            }

            viewPagerAdapter = RestaurantListFragmentPagerAdapter(
                this@HomeFragment,
                restaurantListFragmentList,
                locationLatLng
            )
            viewPager.adapter = viewPagerAdapter
            viewPager.offscreenPageLimit = restaurantCategories.size  // ?????? ???????????? ?????????????????? ?????? ??? ??? ????????? ??????
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.setText(restaurantCategories[position].categoryNameId)
            }.attach()
        }

        if (locationLatLng != viewPagerAdapter.locationLatLng) {
            viewPagerAdapter.locationLatLng = locationLatLng
            viewPagerAdapter.fragmentList.forEach {
                it.viewModel.setLocationLatLng(locationLatLng)
            }
        }

    }

    private fun getMyLocation() {
        if (::locationManager.isInitialized.not()) {
            locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
        val isGpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isGpsEnable) {
            locationPermissionLauncher.launch(locationPermissions)
        }
    }


    @SuppressLint("MissingPermission")
    private fun setMyLocationListener() {
        val minTime: Long = 1500
        val minDistance = 100f
        if (::myLocationListener.isInitialized.not()) {
            myLocationListener = MyLocationListener()
        }
        with(locationManager) {
            requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime, minDistance, myLocationListener
            )
            requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minTime, minDistance, myLocationListener
            )
        }
    }

    inner class MyLocationListener : LocationListener {
        override fun onLocationChanged(location: Location) {
            viewModel.loadReverseGeoInformation(
                LocationLatLngEntity(
                    location.latitude,
                    location.longitude
                )
            )
            removeLocationListener()
        }
    }

    private fun removeLocationListener() {
        if (::locationManager.isInitialized && ::myLocationListener.isInitialized) {
            locationManager.removeUpdates(myLocationListener)
        }
    }

}