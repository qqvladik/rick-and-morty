package by.mankevich.rickandmorty.library.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.mankevich.rickandmorty.domain.locations.LocationEntity

class LocationsRepository private constructor(){

    //private val rickAndMortyApi: RickAndMortyApi
    private var locations: List<LocationEntity> = mutableListOf(
        LocationEntity(1, "Earth", "Planet", "Ours", mutableListOf(2)),
        LocationEntity(2, "Mars", "Planet", "Ours", mutableListOf(1))
    )

    init {
        /*val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        rickAndMortyApi = retrofit.create(RickAndMortyApi::class.java)*/

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

    companion object{
        fun getInstance(): LocationsRepository{
            return LocationsRepository()
        }
    }
}