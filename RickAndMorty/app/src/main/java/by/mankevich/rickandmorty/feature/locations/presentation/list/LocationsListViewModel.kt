package by.mankevich.rickandmorty.feature.locations.presentation.list

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import by.mankevich.rickandmorty.feature.adapter.PagingSource
import by.mankevich.rickandmorty.library.repository.LocationsRepository

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
        PagingSource(locationsRepository)
    }.flow.cachedIn(viewModelScope)

    fun setIsConnect(isConnect: Boolean){
        locationsRepository.isConnect=isConnect
    }

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