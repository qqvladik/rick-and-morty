package by.mankevich.rickandmorty.library.repository

import by.mankevich.photogallery.api.RickAndMortyApi
import by.mankevich.rickandmorty.library.base.BaseFilter
import by.mankevich.rickandmorty.library.db.RickAndMortyDatabase
import by.mankevich.rickandmorty.library.base.BaseRepository
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.library.db.entity.LocationEntity
import by.mankevich.rickandmorty.library.db.entity.parseToCharacterEntity
import by.mankevich.rickandmorty.library.db.entity.parseToLocationEntity

class LocationsRepository private constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    rickAndMortyDatabase: RickAndMortyDatabase
) : BaseRepository<LocationEntity> {

    private val locationDao = rickAndMortyDatabase.getLocationDao()

    var isConnect: Boolean = true
    var isInsert: Boolean = true

    override suspend fun fetchAllByIsConnect(
        limit: Int,
        page: Int,
        search: String,
//        filter: BaseFilter<LocationEntity>
    ): List<LocationEntity> {//todo add Filter
        val locations: List<LocationEntity>
        if (isConnect) {
            locations = fetchAllLocations(page, search)
            if (isInsert) {
                insertListLocations(locations)
            }
        } else {
            locations = getLocations(limit, page, search)
        }
        return locations
    }

    suspend fun fetchAllLocations(
        page: Int,
        search: String,
    ): List<LocationEntity> {
        val locations = ArrayList<LocationEntity>()
        rickAndMortyApi.fetchLocations(page, search).locationsResponse.forEach {
            locations.add(it.parseToLocationEntity())
        }
        return locations
    }

    suspend fun fetchLocation(id: Int): LocationEntity {
        return rickAndMortyApi.fetchLocation(id).parseToLocationEntity()
    }

    suspend fun fetchAndInsertLocation(id: Int): LocationEntity? {
        val location: LocationEntity?
        if (isConnect) {
            location = fetchLocation(id)
            insertLocation(location)
        } else {
            location = getLocation(id)
        }
        return location
    }

    suspend fun insertLocation(location: LocationEntity) {
        insertListLocations(mutableListOf(location))
    }

    suspend fun insertListLocations(locations: List<LocationEntity>) {
        locationDao.insertListLocations(locations)
    }

    suspend fun getLocations(
        limit: Int,
        page: Int,
        search: String
    ): List<LocationEntity> = locationDao.getLocations(
        limit = limit,
        offset = (page - 1) * limit,
        name = search
    )

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