package by.mankevich.rickandmorty.feature.characters.presentation.filter

import androidx.lifecycle.ViewModel
import by.mankevich.rickandmorty.library.repository.CharactersRepository

class FilterCharactersViewModel: ViewModel() {
    private val charactersRepository = CharactersRepository.getInstance()

    val filter = charactersRepository.filter
}