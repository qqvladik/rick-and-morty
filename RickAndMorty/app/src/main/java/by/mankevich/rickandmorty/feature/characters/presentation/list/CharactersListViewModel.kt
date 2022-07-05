package by.mankevich.rickandmorty.feature.characters.presentation.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import by.mankevich.rickandmorty.feature.adapter.PagingSource
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity
import by.mankevich.rickandmorty.library.repository.CharactersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

private const val TAG = "RAMCharactersViewModel"

class CharactersListViewModel : ViewModel() {
    private val charactersRepository = CharactersRepository.getInstance()
    private val _searchFlow = MutableStateFlow("")

    val data: Flow<PagingData<CharacterEntity>>

    init {
        @OptIn(ExperimentalCoroutinesApi::class)
        data = _searchFlow.flatMapLatest {
            Pager(
                PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false,
                    initialLoadSize = 20
                ),
            ) {
                PagingSource(charactersRepository, it)
            }.flow.cachedIn(viewModelScope)
        }
        Log.d(TAG, "data subscribed")
    }

    fun setIsConnect(isConnect: Boolean){
        charactersRepository.isConnect=isConnect
    }

    fun onSearchChanged(search: String) {
        _searchFlow.value = search
    }

}