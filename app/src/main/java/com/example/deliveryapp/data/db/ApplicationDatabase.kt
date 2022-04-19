package com.example.deliveryapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.deliveryapp.data.db.dao.LocationDao
import com.example.deliveryapp.data.db.dao.RestaurantDao
import com.example.deliveryapp.data.entity.location.LocationLatLngEntity
import com.example.deliveryapp.data.entity.restaurant.RestaurantEntity

@Database(
    entities = [LocationLatLngEntity::class, RestaurantEntity::class],
    version = 1,
    exportSchema = false
)

abstract class ApplicationDatabase: RoomDatabase() {

    companion object {
        const val DB_NAME = "ApplicationDataBase.db"
    }

    abstract fun LocationDao(): LocationDao

    abstract fun RestaurantDao(): RestaurantDao
}
