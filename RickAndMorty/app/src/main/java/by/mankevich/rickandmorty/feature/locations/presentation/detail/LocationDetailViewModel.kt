package by.mankevich.rickandmorty.feature.locations.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import by.mankevich.rickandmorty.library.db.CharacterEntity
import by.mankevich.rickandmorty.library.db.LocationEntity
import by.mankevich.rickandmorty.library.repository.CharactersRepository
import by.mankevich.rickandmorty.library.repository.LocationsRepository

class LocationDetailViewModel : ViewModel() {
    private val locationsRepository = LocationsRepository.getInstance()
    private val charactersRepository = CharactersRepository.getInstance()
    private val locationIdLiveData = MutableLiveData<Int>()
    private val charactersIdListLiveData = MutableLiveData<List<Int>>()

    var locationLiveData: LiveData<LocationEntity?> =
        Transformations.switchMap(locationIdLiveData) { locationId ->
            locationsRepository.getLocation(locationId)
        }

    var charactersLiveData: LiveData<List<CharacterEntity>> =
        Transformations.switchMap(charactersIdListLiveData) { charactersIdList ->
            charactersRepository.getMultipleCharacters(charactersIdList)
        }

    fun loadCharacters(charactersIdList: List<Int>) {
        charactersIdListLiveData.value = charactersIdList
    }

    fun loadLocation(locationId: Int) {
        locationIdLiveData.value = locationId
    }
}