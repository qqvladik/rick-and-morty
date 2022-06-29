package by.mankevich.rickandmorty.library.repository

import android.content.Context
import by.mankevich.photogallery.api.RickAndMortyApi
import by.mankevich.rickandmorty.library.db.entity.LocationEntity
import by.mankevich.rickandmorty.library.db.entity.parseToLocationEntity

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

    suspend fun getAllLocations(): List<LocationEntity> {
        return ArrayList(locations)
    }

    suspend fun getLocation(id: Int): LocationEntity?{
        return locations[id-1].copy()
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