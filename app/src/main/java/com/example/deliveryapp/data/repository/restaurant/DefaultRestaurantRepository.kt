package com.example.deliveryapp.data.repository.restaurant

import com.example.deliveryapp.data.entity.location.LocationLatLngEntity
import com.example.deliveryapp.data.entity.restaurant.RestaurantEntity
import com.example.deliveryapp.data.network.MapApiService
import com.example.deliveryapp.screen.home.restaurant.RestaurantCategory
import com.example.deliveryapp.util.provider.ResourcesProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultRestaurantRepository(
    private val mapApiService: MapApiService,
    private val resourcesProvider: ResourcesProvider,
    private val ioDispatcher: CoroutineDispatcher
) : RestaurantRepository {

    override suspend fun getList(
        restaurantCategory: RestaurantCategory,
        locationLatLngEntity: LocationLatLngEntity
    ): List<RestaurantEntity> = withContext(ioDispatcher) {

        val response = mapApiService.getSearchLocationAround(
            categories = resourcesProvider.getString(restaurantCategory.categoryTypeId),
            centerLat = locationLatLngEntity.latitude.toString(),
            centerLon = locationLatLngEntity.longitude.toString(),
            searchType = "name",
            radius = "1", // 반경 1km
            resCoordType = "EPSG3857",  // 특정 스펙
            searchtypCd = "A",          //
            reqCoordType = "WGS84GEO"   // 특정 스펙
        )

        if (response.isSuccessful) {
            response.body()?.searchPoiInfo?.pois?.poi?.mapIndexed { index, poi ->
                RestaurantEntity(
                    id = hashCode().toLong(),
                    restaurantInfoId = (1..10).random().toLong(),
                    restaurantCategory = restaurantCategory,
                    restaurantTitle = poi.name ?: "제목 없음",
                    restaurantImageUrl = "https://picsum.photos/200",
                    grade = (1 until 5).random() + ((0..10).random() / 10f),
                    reviewCount = (0 until 200).random(),
                    deliveryTimeRange = Pair((0..20).random(), (40..60).random()),
                    deliveryTipRange = Pair((0..1000).random(), (2000..4000).random()),
                )
            } ?: listOf()
        } else {
            listOf()
        }




//        listOf(
//            RestaurantEntity(
//                id = 0,
//                restaurantInfoId = 0,
//                restaurantCategory = RestaurantCategory.ALL,
//                restaurantTitle = "마포화로집",
//                restaurantImageUrl = "https://picsum.photos/200",
//                grade = (1 until 5).random() + ((0..10).random() / 10f),
//                reviewCount = (0 until 200).random(),
//                deliveryTimeRange = Pair(0, 20),
//                deliveryTipRange = Pair(0, 2000)
//            ),
//            RestaurantEntity(
//                id = 1,
//                restaurantInfoId = 1,
//                restaurantCategory = RestaurantCategory.ALL,
//                restaurantTitle = "옛날우동&덮밥",
//                restaurantImageUrl = "https://picsum.photos/200",
//                grade = (1 until 5).random() + ((0..10).random() / 10f),
//                reviewCount = (0 until 200).random(),
//                deliveryTimeRange = Pair(0, 20),
//                deliveryTipRange = Pair(0, 2000)
//            ),
//            RestaurantEntity(
//                id = 2,
//                restaurantInfoId = 2,
//                restaurantCategory = RestaurantCategory.ALL,
//                restaurantTitle = "마스터석쇠불고기&냉면plus",
//                restaurantImageUrl = "https://picsum.photos/200",
//                grade = (1 until 5).random() + ((0..10).random() / 10f),
//                reviewCount = (0 until 200).random(),
//                deliveryTimeRange = Pair(0, 20),
//                deliveryTipRange = Pair(0, 2000)
//            ),
//            RestaurantEntity(
//                id = 3,
//                restaurantInfoId = 3,
//                restaurantCategory = RestaurantCategory.ALL,
//                restaurantTitle = "마스터통삼겹",
//                restaurantImageUrl = "https://picsum.photos/200",
//                grade = (1 until 5).random() + ((0..10).random() / 10f),
//                reviewCount = (0 until 200).random(),
//                deliveryTimeRange = Pair(0, 20),
//                deliveryTipRange = Pair(0, 2000)
//            ),
//            RestaurantEntity(
//                id = 4,
//                restaurantInfoId = 4,
//                restaurantCategory = RestaurantCategory.ALL,
//                restaurantTitle = "창영이 족발&보쌈",
//                restaurantImageUrl = "https://picsum.photos/200",
//                grade = (1 until 5).random() + ((0..10).random() / 10f),
//                reviewCount = (0 until 200).random(),
//                deliveryTimeRange = Pair(0, 20),
//                deliveryTipRange = Pair(0, 2000)
//            ),
//            RestaurantEntity(
//                id = 5,
//                restaurantInfoId = 5,
//                restaurantCategory = RestaurantCategory.ALL,
//                restaurantTitle = "콩나물국밥&코다리조림 콩심 인천논현점",
//                restaurantImageUrl = "https://picsum.photos/200",
//                grade = (1 until 5).random() + ((0..10).random() / 10f),
//                reviewCount = (0 until 200).random(),
//                deliveryTimeRange = Pair(0, 20),
//                deliveryTipRange = Pair(0, 2000)
//            ),
//            RestaurantEntity(
//                id = 6,
//                restaurantInfoId = 6,
//                restaurantCategory = RestaurantCategory.ALL,
//                restaurantTitle = "김여사 칼국수&냉면 논현점",
//                restaurantImageUrl = "https://picsum.photos/200",
//                grade = (1 until 5).random() + ((0..10).random() / 10f),
//                reviewCount = (0 until 200).random(),
//                deliveryTimeRange = Pair(0, 20),
//                deliveryTipRange = Pair(0, 2000)
//            ),
//            RestaurantEntity(
//                id = 7,
//                restaurantInfoId = 7,
//                restaurantCategory = RestaurantCategory.ALL,
//                restaurantTitle = "돈키호테",
//                restaurantImageUrl = "https://picsum.photos/200",
//                grade = (1 until 5).random() + ((0..10).random() / 10f),
//                reviewCount = (0 until 200).random(),
//                deliveryTimeRange = Pair(0, 20),
//                deliveryTipRange = Pair(0, 2000)
//            ),
//        )
    }

}