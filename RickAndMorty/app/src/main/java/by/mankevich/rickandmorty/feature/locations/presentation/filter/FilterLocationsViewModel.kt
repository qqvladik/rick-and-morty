package by.mankevich.rickandmorty.feature.locations.presentation.filter

import androidx.lifecycle.ViewModel
import by.mankevich.rickandmorty.library.repository.CharactersRepository
import by.mankevich.rickandmorty.library.repository.EpisodesRepository
import by.mankevich.rickandmorty.library.repository.LocationsRepository

class FilterLocationsViewModel: ViewModel() {
    private val locationsRepository = LocationsRepository.getInstance()

    val filter = locationsRepository.filter
}