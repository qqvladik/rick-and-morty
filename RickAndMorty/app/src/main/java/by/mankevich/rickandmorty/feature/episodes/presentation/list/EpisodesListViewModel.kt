package by.mankevich.rickandmorty.feature.episodes.presentation.list

import androidx.lifecycle.ViewModel
import by.mankevich.rickandmorty.library.repository.EpisodesRepository

class EpisodesListViewModel: ViewModel() {
    private val episodesRepository = EpisodesRepository.getInstance()
    val episodesLiveData = episodesRepository.getAllEpisodes()
}