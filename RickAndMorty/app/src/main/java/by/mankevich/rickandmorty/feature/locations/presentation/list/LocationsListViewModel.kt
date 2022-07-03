package by.mankevich.rickandmorty.feature.locations.presentation.list

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import by.mankevich.rickandmorty.feature.adapter.paging.EpisodesPagingSource
import by.mankevich.rickandmorty.feature.adapter.paging.LocationsPagingSource
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity
import by.mankevich.rickandmorty.library.db.entity.LocationEntity
import by.mankevich.rickandmorty.library.repository.LocationsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "RAMLocationsViewModel"

class LocationsListViewModel: ViewModel() {
    private val locationsRepository = LocationsRepository.getInstance()

    val data = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
    ) {
        LocationsPagingSource(locationsRepository)
    }.flow.cachedIn(viewModelScope)

//    private val _locationsLiveData = MutableLiveData<List<LocationEntity>>()
//    val locationsLiveData: LiveData<List<LocationEntity>> = _locationsLiveData// = loadLocations() //= locationsRepository.getAllLocations()
//
//    fun loadLocations() {
//        viewModelScope.launch {
//            try{
//            _locationsLiveData.postValue(locationsRepository.fetchAllAndInsertLocations())//todo можно обернуть в state
//            } catch (e: Exception) {
//                Log.d(TAG, "loadLocations failure")
//            }
//        }
//    }
}