package by.mankevich.rickandmorty.feature.episodes.presentation.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity
import by.mankevich.rickandmorty.library.repository.EpisodesRepository
import kotlinx.coroutines.launch

private const val TAG = "RAMEpisodesViewModel"

class EpisodesListViewModel: ViewModel() {
    private val episodesRepository = EpisodesRepository.getInstance()

    private val _episodesLiveData = MutableLiveData<List<EpisodeEntity>>()
    val episodesLiveData: LiveData<List<EpisodeEntity>> = _episodesLiveData

    fun loadEpisodes(){
        viewModelScope.launch {
            try {
            _episodesLiveData.postValue(episodesRepository.fetchAllAndInsertEpisodes())
            } catch (e: Exception) {
                Log.d(TAG, "loadEpisodes failure")
            }
        }
    }
}