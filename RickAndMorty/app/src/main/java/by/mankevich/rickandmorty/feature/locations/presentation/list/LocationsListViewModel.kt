package by.mankevich.rickandmorty.feature.locations.presentation.list

import android.util.Log
import androidx.lifecycle.*
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity
import by.mankevich.rickandmorty.library.db.entity.LocationEntity
import by.mankevich.rickandmorty.library.repository.LocationsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "RAMLocationsViewModel"

class LocationsListViewModel: ViewModel() {
    private val locationsRepository = LocationsRepository.getInstance()

    private val _locationsLiveData = MutableLiveData<List<LocationEntity>>()
    val locationsLiveData: LiveData<List<LocationEntity>> = _locationsLiveData// = loadLocations() //= locationsRepository.getAllLocations()

    fun loadLocations() {
        viewModelScope.launch {
            try{
            _locationsLiveData.postValue(locationsRepository.fetchAllAndInsertLocations())//todo можно обернуть в state
            } catch (e: Exception) {
                Log.d(TAG, "loadLocations failure")
            }
        }
        //через viewModelScope работает гораздо быстрее чем блок ниже, даже связь с инетом через liveData работала быстрее
//        locationsLiveData = liveData(Dispatchers.IO) {
//            try {
//                emit(locationsRepository.fetchAllLocations())
//            } catch (exception: Exception) {
//                emit(null)
//                Log.d(TAG, "loadLocations failure")//todo можно обернуть в state
//            }
//        }
    }
}