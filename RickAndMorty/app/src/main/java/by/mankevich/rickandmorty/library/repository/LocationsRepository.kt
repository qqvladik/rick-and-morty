package by.mankevich.rickandmorty.library.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.mankevich.photogallery.api.RickAndMortyApi
import by.mankevich.rickandmorty.library.db.LocationEntity
import by.mankevich.rickandmorty.library.db.parseToLocationEntity

class LocationsRepository private constructor(
    private val rickAndMortyApi: RickAndMortyApi
){

    private var locations: List<LocationEntity> = mutableListOf(
        LocationEntity(1, "Earth", "Planet", "Ours", mutableListOf(2)),
        LocationEntity(2, "Mars", "Planet", "Ours", mutableListOf(1))
    )

    suspend fun fetchAllLocations(): List<LocationEntity>{
        val locations = ArrayList<LocationEntity>()
        rickAndMortyApi.fetchLocations().locationsResponse.forEach{
            locations.add(it.parseToLocationEntity())
        }
        return locations
    }

    fun getAllLocations(): LiveData<List<LocationEntity>> {
        val locationsLiveData: MutableLiveData<List<LocationEntity>> = MutableLiveData()
        locationsLiveData.value = ArrayList(locations)
        return locationsLiveData
    }

    fun getLocation(id: Int): LiveData<LocationEntity?>{
        val locationEntity = locations[id-1].copy()
        return MutableLiveData(locationEntity)
    }

    companion object {
        private var INSTANCE: LocationsRepository? = null

        fun initialize(appContext: Context, rickAndMortyApi: RickAndMortyApi) {
            INSTANCE = LocationsRepository(rickAndMortyApi)
        }

        fun getInstance(): LocationsRepository {
            return INSTANCE
                ?: throw IllegalStateException("Episodes repository must be initialized")
        }
    }
}