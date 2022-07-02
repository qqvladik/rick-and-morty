package by.mankevich.rickandmorty.feature.locations.presentation.detail

import androidx.lifecycle.*
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.library.db.entity.LocationEntity
import by.mankevich.rickandmorty.library.repository.CharactersRepository
import by.mankevich.rickandmorty.library.repository.LocationsRepository
import kotlinx.coroutines.launch

class LocationDetailViewModel : ViewModel() {
    private val locationsRepository = LocationsRepository.getInstance()
    private val charactersRepository = CharactersRepository.getInstance()

    private val _locationLiveData = MutableLiveData<LocationEntity>()
    val locationLiveData: LiveData<LocationEntity> = _locationLiveData

    var charactersLiveData: LiveData<List<CharacterEntity>> =
        Transformations.switchMap(_locationLiveData) { location ->
            val charactersLiveData = MutableLiveData<List<CharacterEntity>>()
            viewModelScope.launch {
                charactersLiveData.value=charactersRepository.fetchMultipleAndInsertCharacters(location!!.residents)
            }
            return@switchMap charactersLiveData
        }

    fun loadLocation(locationId: Int) {
        viewModelScope.launch {
            _locationLiveData.value = locationsRepository.getLocation(locationId)
        }
    }
}