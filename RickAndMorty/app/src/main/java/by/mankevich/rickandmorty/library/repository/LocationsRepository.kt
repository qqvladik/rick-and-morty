package by.mankevich.rickandmorty.library.repository

import by.mankevich.photogallery.api.RickAndMortyApi
import by.mankevich.rickandmorty.library.db.RickAndMortyDatabase
import by.mankevich.rickandmorty.library.db.base.BaseRepository
import by.mankevich.rickandmorty.library.db.entity.LocationEntity
import by.mankevich.rickandmorty.library.db.entity.parseToLocationEntity

class LocationsRepository private constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    rickAndMortyDatabase: RickAndMortyDatabase
) : BaseRepository {

    private val locationDao = rickAndMortyDatabase.getLocationDao()

    private var locations: List<LocationEntity> = mutableListOf(
        LocationEntity(1, "Earth", "Planet", "Ours", mutableListOf(2)),
        LocationEntity(2, "Mars", "Planet", "Ours", mutableListOf(1))
    )

    suspend fun fetchAllLocations(): List<LocationEntity> {
        val locations = ArrayList<LocationEntity>()
        rickAndMortyApi.fetchLocations().locationsResponse.forEach {
            locations.add(it.parseToLocationEntity())
        }
        return locations
    }

    suspend fun fetchAllAndInsertLocations(): List<LocationEntity> {
        val locations = ArrayList<LocationEntity>()
        rickAndMortyApi.fetchLocations().locationsResponse.forEach {
            locations.add(it.parseToLocationEntity())
        }
        insertListLocations(locations)
        return locations
    }

    suspend fun fetchLocation(id: Int): LocationEntity {
        return rickAndMortyApi.fetchLocation(id).parseToLocationEntity()
    }

    suspend fun fetchAndInsertLocation(id: Int): LocationEntity {
        val location = rickAndMortyApi.fetchLocation(id).parseToLocationEntity()
        insertLocation(location)
        return location
    }

    suspend fun insertLocation(location: LocationEntity) {
        insertListLocations(mutableListOf(location))
    }

    suspend fun insertListLocations(locations: List<LocationEntity>) {
        locationDao.insertListLocations(locations)
    }

    suspend fun getAllLocations(): List<LocationEntity> {
        return ArrayList(locations)
    }

    suspend fun getLocation(id: Int): LocationEntity? {
        return locationDao.getLocationById(id)
    }

    companion object {
        private var INSTANCE: LocationsRepository? = null

        fun initialize(
            rickAndMortyApi: RickAndMortyApi,
            rickAndMortyDatabase: RickAndMortyDatabase
        ) {
            INSTANCE = LocationsRepository(rickAndMortyApi, rickAndMortyDatabase)
        }

        fun getInstance(): LocationsRepository {
            return INSTANCE
                ?: throw IllegalStateException("Episodes repository must be initialized")
        }
    }
}