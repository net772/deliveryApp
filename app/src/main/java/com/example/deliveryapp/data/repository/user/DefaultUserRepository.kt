package com.example.deliveryapp.data.repository.user

import com.example.deliveryapp.data.db.dao.LocationDao
import com.example.deliveryapp.data.db.dao.RestaurantDao
import com.example.deliveryapp.data.entity.location.LocationLatLngEntity
import com.example.deliveryapp.data.entity.restaurant.RestaurantEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultUserRepository(
    private val locationDao: LocationDao,
    private val restaurantDao: RestaurantDao,
    private val ioDispatcher: CoroutineDispatcher
): UserRepository {

    override suspend fun getUserLocation(): LocationLatLngEntity? = withContext(ioDispatcher) {
        locationDao.get(-1)
    }

    override suspend fun insertUserLocation(
        locationLatLngEntity: LocationLatLngEntity
    ) = withContext(ioDispatcher) {
        locationDao.insert(locationLatLngEntity)
    }

    override suspend fun getUserLikedRestaurant(restaurantTitle: String): RestaurantEntity? = withContext(ioDispatcher) {
        restaurantDao.get(restaurantTitle)
    }

    override suspend fun insertUserLikedRestaurant(restaurantEntity: RestaurantEntity) = withContext(ioDispatcher) {
        restaurantDao.insert(restaurantEntity)
    }

    override suspend fun getAllUserLikedRestaurant(): List<RestaurantEntity> = withContext(ioDispatcher) {
        restaurantDao.getAll()
    }

    override suspend fun deleteUserLikedRestaurant(restaurantTitle: String) {
        restaurantDao.delete(restaurantTitle)
    }

}
