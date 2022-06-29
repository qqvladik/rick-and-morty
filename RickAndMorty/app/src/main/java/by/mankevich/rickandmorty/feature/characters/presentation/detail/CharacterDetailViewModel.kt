package by.mankevich.rickandmorty.feature.characters.presentation.detail

import androidx.lifecycle.*
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity
import by.mankevich.rickandmorty.library.repository.CharactersRepository
import by.mankevich.rickandmorty.library.repository.EpisodesRepository
import kotlinx.coroutines.launch

class CharacterDetailViewModel : ViewModel() {
    private val charactersRepository = CharactersRepository.getInstance()
    private val episodesRepository = EpisodesRepository.getInstance()

    private val _characterLiveData = MutableLiveData<CharacterEntity?>()
    val characterLiveData: LiveData<CharacterEntity?> = _characterLiveData

    var episodesLiveData: LiveData<List<EpisodeEntity>> =
        Transformations.switchMap(_characterLiveData) { character ->
            val episodesLiveData = MutableLiveData<List<EpisodeEntity>>()
            viewModelScope.launch {
                episodesLiveData.value=episodesRepository.getMultipleEpisodes(character!!.episode)
            }
            return@switchMap episodesLiveData
        }

    fun loadCharacterFull(characterId: Int) {
        viewModelScope.launch {
            _characterLiveData.value = charactersRepository.getCharacter(characterId)
        }
    }
}