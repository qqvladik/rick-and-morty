package by.mankevich.rickandmorty.feature.episodes.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import by.mankevich.rickandmorty.library.db.CharacterEntity
import by.mankevich.rickandmorty.library.db.EpisodeEntity
import by.mankevich.rickandmorty.library.repository.CharactersRepository
import by.mankevich.rickandmorty.library.repository.EpisodesRepository

class EpisodeDetailViewModel : ViewModel() {
    private val episodesRepository = EpisodesRepository.getInstance()
    private val charactersRepository = CharactersRepository.getInstance()
    private val episodeIdLiveData = MutableLiveData<Int>()
    private val charactersIdListLiveData = MutableLiveData<List<Int>>()

    var episodeLiveData: LiveData<EpisodeEntity?> =
        Transformations.switchMap(episodeIdLiveData) { episodeId ->
            episodesRepository.getEpisode(episodeId)
        }

    var charactersLiveData: LiveData<List<CharacterEntity>> =
        Transformations.switchMap(charactersIdListLiveData) { charactersIdList ->
            charactersRepository.getMultipleCharacters(charactersIdList)
        }

    fun loadCharacters(charactersIdList: List<Int>) {
        charactersIdListLiveData.value = charactersIdList
    }

    fun loadEpisode(episodeId: Int) {
        episodeIdLiveData.value = episodeId
    }
}