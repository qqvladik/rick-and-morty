package by.mankevich.rickandmorty.feature.episodes.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import by.mankevich.rickandmorty.feature.adapter.PagingSource
import by.mankevich.rickandmorty.library.repository.EpisodesRepository
import by.mankevich.rickandmorty.library.repository.filter.FilterCharacters
import by.mankevich.rickandmorty.library.repository.filter.FilterEpisodes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

private const val TAG = "RAMEpisodesViewModel"

class EpisodesListViewModel : ViewModel() {
    private val episodesRepository = EpisodesRepository.getInstance()
    private val _searchFlow = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val data = _searchFlow.flatMapLatest {
        Pager(
            PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
        ) {
            PagingSource(episodesRepository, it)
        }.flow.cachedIn(viewModelScope)
    }

    fun setIsConnect(isConnect: Boolean) {
        episodesRepository.isConnect = isConnect
    }

    fun onSearchChanged(search: String) {
        _searchFlow.value = search
    }

}