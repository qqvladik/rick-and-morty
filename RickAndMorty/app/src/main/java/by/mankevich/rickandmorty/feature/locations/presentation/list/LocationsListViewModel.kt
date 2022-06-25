package by.mankevich.rickandmorty.feature.locations.presentation.list

import androidx.lifecycle.ViewModel
import by.mankevich.rickandmorty.library.repository.LocationsRepository

class LocationsListViewModel: ViewModel() {
    private val locationsRepository = LocationsRepository.getInstance()
    val locationsLiveData = locationsRepository.getAllLocations()
}