package by.mankevich.rickandmorty.feature.characters.presentation.detail

import android.util.Log
import androidx.lifecycle.*
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity
import by.mankevich.rickandmorty.library.repository.CharactersRepository
import by.mankevich.rickandmorty.library.repository.EpisodesRepository
import by.mankevich.rickandmorty.library.repository.LocationsRepository
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "RAMCharacterViewModel"

class CharacterDetailViewModel : ViewModel() {
    private val charactersRepository = CharactersRepository.getInstance()
    private val episodesRepository = EpisodesRepository.getInstance()
    private val locationsRepository = LocationsRepository.getInstance()

    private val _characterLiveData = MutableLiveData<CharacterEntity?>()
    val characterLiveData: LiveData<CharacterEntity?> = _characterLiveData
    var isOriginAvailable = false
    var isLocationAvailable = false

    var episodesLiveData: LiveData<List<EpisodeEntity>> =
        Transformations.switchMap(_characterLiveData) { character ->
            val episodesLiveData = MutableLiveData<List<EpisodeEntity>>()
            viewModelScope.launch {
                try {
                    episodesLiveData.value =
                        episodesRepository.fetchMultipleEpisodesByIsConnect(character!!.episode)
                    isOriginAvailable =
                        locationsRepository.fetchAndInsertLocation(character.origin.id) != null
                    isLocationAvailable =
                        locationsRepository.fetchAndInsertLocation(character.location.id) != null
                }catch (e: Exception){
                    Log.d(TAG, e.toString())
                }
            }
            return@switchMap episodesLiveData
        }

    fun loadCharacterFull(characterId: Int) {
        viewModelScope.launch {
            _characterLiveData.value = charactersRepository.getCharacter(characterId)
        }
    }

    fun setIsConnect(isConnect: Boolean){
        charactersRepository.isConnect=isConnect
        episodesRepository.isConnect=isConnect
        locationsRepository.isConnect=isConnect
    }
}