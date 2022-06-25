package by.mankevich.rickandmorty.feature.characters.presentation.list

import androidx.lifecycle.ViewModel
import by.mankevich.rickandmorty.library.repository.CharactersRepository

class CharactersListViewModel: ViewModel() {
    private val charactersRepository = CharactersRepository.getInstance()
    val charactersLiveData = charactersRepository.getAllCharacters()
}