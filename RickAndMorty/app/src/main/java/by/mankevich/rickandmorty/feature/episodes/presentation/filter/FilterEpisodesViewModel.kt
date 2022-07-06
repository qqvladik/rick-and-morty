package by.mankevich.rickandmorty.feature.episodes.presentation.filter

import androidx.lifecycle.ViewModel
import by.mankevich.rickandmorty.library.repository.CharactersRepository
import by.mankevich.rickandmorty.library.repository.EpisodesRepository

class FilterEpisodesViewModel: ViewModel() {
    private val episodesRepository = EpisodesRepository.getInstance()

    val filter = episodesRepository.filter
}