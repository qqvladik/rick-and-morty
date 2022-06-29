package by.mankevich.rickandmorty.feature.episodes.presentation.detail

import androidx.lifecycle.*
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity
import by.mankevich.rickandmorty.library.repository.CharactersRepository
import by.mankevich.rickandmorty.library.repository.EpisodesRepository
import kotlinx.coroutines.launch

class EpisodeDetailViewModel : ViewModel() {
    private val episodesRepository = EpisodesRepository.getInstance()
    private val charactersRepository = CharactersRepository.getInstance()

    private val _episodeLiveData = MutableLiveData<EpisodeEntity?>()
    val episodeLiveData: LiveData<EpisodeEntity?> = _episodeLiveData

    var charactersLiveData: LiveData<List<CharacterEntity>> =
        Transformations.switchMap(_episodeLiveData) { episode ->
            val charactersLiveData = MutableLiveData<List<CharacterEntity>>()
            viewModelScope.launch {
                charactersLiveData.value=charactersRepository.getMultipleCharacters(episode!!.characters)
            }
            return@switchMap charactersLiveData
        }

    fun loadEpisodeFull(episodeId: Int) {
        viewModelScope.launch {
            _episodeLiveData.value = episodesRepository.getEpisode(episodeId)
        }
    }
}