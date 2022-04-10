package com.example.deliveryapp.data.entity.location

import android.os.Parcelable
import com.example.deliveryapp.data.entity.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationLatLngEntity(
    val latitude: Double,
    val longitude: Double,
    override val id: Long = -1
): Entity, Parcelable