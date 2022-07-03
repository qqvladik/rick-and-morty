package by.mankevich.rickandmorty.feature.characters.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import by.mankevich.rickandmorty.feature.adapter.PagingSource
import by.mankevich.rickandmorty.library.repository.CharactersRepository

private const val TAG = "RAMCharactersViewModel"

class CharactersListViewModel : ViewModel() {
    private val charactersRepository = CharactersRepository.getInstance()

//    private val _charactersLiveData = MutableLiveData<List<CharacterEntity>>()
//    val charactersLiveData: LiveData<List<CharacterEntity>> = _charactersLiveData

    val data = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
    ) {
        PagingSource(charactersRepository)
    }.flow.cachedIn(viewModelScope)

//    private val _stateCharactersLiveData = MutableLiveData<State<List<CharacterEntity>>>()
//    val stateCharactersLiveData: LiveData<State<List<CharacterEntity>>> = _stateCharactersLiveData

//    fun loadCharacters() {
//        viewModelScope.launch {
//            //_charactersLiveData.postValue(State.Loading())//todo можно обернуть в state
//            try {
//                _charactersLiveData.postValue(charactersRepository.fetchAllCharacters(1))
//            } catch (e: Exception) {
//                Log.d(TAG, "loadCharacters failure")
//                //_charactersLiveData.postValue(State.Error(e))
//            }
//        }
//    }
}