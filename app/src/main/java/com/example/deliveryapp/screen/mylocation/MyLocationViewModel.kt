package com.example.deliveryapp.screen.mylocation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.data.entity.location.LocationLatLngEntity
import com.example.deliveryapp.data.entity.location.MapSearchInfoEntity
import com.example.deliveryapp.data.repository.map.MapRepository
import com.example.deliveryapp.data.repository.user.UserRepository
import com.example.deliveryapp.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyLocationViewModel(
    private val mapSearchInfoEntity: MapSearchInfoEntity,
    private val mapRepository: MapRepository,
    private val userRepository: UserRepository
): BaseViewModel() {

    val myLocationStateLiveData = MutableLiveData<MyLocationState>(MyLocationState.Uninitialized)

    fun fetchLocationData(): Job = viewModelScope.launch {
        myLocationStateLiveData.value = MyLocationState.Loading
        myLocationStateLiveData.value = MyLocationState.Success(
            mapSearchInfoEntity
        )
    }

    fun changeLocationInfo(
        locationLatLngEntity: LocationLatLngEntity
    ) = viewModelScope.launch {
        val addressInfo = mapRepository.getReverseGeoInformation(locationLatLngEntity)
        addressInfo?.let { info ->
            myLocationStateLiveData.value = MyLocationState.Success(
                MapSearchInfoEntity(
                    fullAddress = info.fullAddress ?: "주소 정보 없음",
                    name = info.buildingName ?: "빌딩정보 없음",
                    locationLatLng = locationLatLngEntity
                )
            )
        } ?: kotlin.run {

        }
    }

    fun confirmSelectLocation() = viewModelScope.launch {
        when(val data = myLocationStateLiveData.value) {
            is MyLocationState.Success -> {
                userRepository.insertUserLocation(data.mapSearchInfoEntity.locationLatLng)
                myLocationStateLiveData.value = MyLocationState.Confirm(
                    data.mapSearchInfoEntity
                )
            }
        }
    }
}