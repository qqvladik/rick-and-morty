package by.mankevich.rickandmorty.feature.episodes.presentation.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import by.mankevich.rickandmorty.feature.adapter.paging.CharactersPagingSource
import by.mankevich.rickandmorty.feature.adapter.paging.EpisodesPagingSource
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity
import by.mankevich.rickandmorty.library.repository.EpisodesRepository
import kotlinx.coroutines.launch

private const val TAG = "RAMEpisodesViewModel"

class EpisodesListViewModel: ViewModel() {
    private val episodesRepository = EpisodesRepository.getInstance()

    val data = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
    ) {
        EpisodesPagingSource(episodesRepository)
    }.flow.cachedIn(viewModelScope)

//    private val _episodesLiveData = MutableLiveData<List<EpisodeEntity>>()
//    val episodesLiveData: LiveData<List<EpisodeEntity>> = _episodesLiveData
//
//    fun loadEpisodes(){
//        viewModelScope.launch {
//            try {
//            _episodesLiveData.postValue(episodesRepository.fetchAllAndInsertEpisodes())
//            } catch (e: Exception) {
//                Log.d(TAG, "loadEpisodes failure")
//            }
//        }
//    }
}