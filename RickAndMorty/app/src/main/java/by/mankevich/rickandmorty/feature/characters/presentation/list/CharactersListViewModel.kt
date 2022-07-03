package by.mankevich.rickandmorty.feature.characters.presentation.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.feature.adapter.paging.CharactersPagingSource
import by.mankevich.rickandmorty.library.repository.CharactersRepository
import kotlinx.coroutines.launch

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
        CharactersPagingSource(charactersRepository)
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