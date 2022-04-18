package com.example.deliveryapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.deliveryapp.data.db.dao.LocationDao
import com.example.deliveryapp.data.entity.location.LocationLatLngEntity

@Database(
    entities = [LocationLatLngEntity::class],
    version = 1,
    exportSchema = false
)

abstract class ApplicationDatabase: RoomDatabase() {

    companion object {
        const val DB_NAME = "ApplicationDataBase.db"
    }

    abstract fun LocationDao(): LocationDao
}
