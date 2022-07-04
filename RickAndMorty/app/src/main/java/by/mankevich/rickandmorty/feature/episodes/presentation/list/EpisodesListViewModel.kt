package by.mankevich.rickandmorty.feature.episodes.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import by.mankevich.rickandmorty.feature.adapter.PagingSource
import by.mankevich.rickandmorty.library.repository.EpisodesRepository

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
        PagingSource(episodesRepository)
    }.flow.cachedIn(viewModelScope)

    fun setIsConnect(isConnect: Boolean){
        episodesRepository.isConnect=isConnect
    }

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