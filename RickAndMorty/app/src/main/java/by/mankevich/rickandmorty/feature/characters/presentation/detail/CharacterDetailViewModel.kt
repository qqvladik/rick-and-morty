package by.mankevich.rickandmorty.feature.characters.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import by.mankevich.rickandmorty.domain.characters.CharacterEntity
import by.mankevich.rickandmorty.domain.episodes.EpisodeEntity
import by.mankevich.rickandmorty.library.repository.CharactersRepository
import by.mankevich.rickandmorty.library.repository.EpisodesRepository
import java.util.*

class CharacterDetailViewModel : ViewModel() {
    private val charactersRepository = CharactersRepository.getInstance()
    private val episodesRepository = EpisodesRepository.getInstance()
    private val characterIdLiveData = MutableLiveData<Int>()
    private val episodesIdListLiveData = MutableLiveData<List<Int>>()

    var characterLiveData: LiveData<CharacterEntity?> =
        Transformations.switchMap(characterIdLiveData) { characterId ->
            charactersRepository.getCharacter(characterId)
        }

    var episodesLiveData: LiveData<List<EpisodeEntity>> =
        Transformations.switchMap(episodesIdListLiveData) { episodesIdList ->
            episodesRepository.getMultipleEpisodes(episodesIdList)
        }

    fun loadEpisodes(episodesIdList: List<Int>) {
        episodesIdListLiveData.value = episodesIdList
    }

    fun loadCharacter(characterId: Int) {
        characterIdLiveData.value = characterId
    }
}