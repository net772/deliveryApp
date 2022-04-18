package com.example.deliveryapp.data.repository.user

import com.example.deliveryapp.data.entity.location.LocationLatLngEntity

interface UserRepository {

    suspend fun getUserLocation(): LocationLatLngEntity?

    suspend fun insertUserLocation(locationLatLngEntity: LocationLatLngEntity)


}
