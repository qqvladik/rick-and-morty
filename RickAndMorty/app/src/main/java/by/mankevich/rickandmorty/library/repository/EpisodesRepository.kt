package by.mankevich.rickandmorty.library.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.mankevich.photogallery.api.RickAndMortyApi
import by.mankevich.rickandmorty.library.db.EpisodeEntity
import by.mankevich.rickandmorty.library.db.parseToCharacterEntity
import by.mankevich.rickandmorty.library.db.parseToEpisodeEntity

private const val TAG = "RAMEpisodesRepository"

class EpisodesRepository private constructor(
    private val rickAndMortyApi: RickAndMortyApi
) {

    private var episodes: List<EpisodeEntity> = mutableListOf(
        EpisodeEntity(1, "Birthday", "October 09, 1998", "S01E01", mutableListOf(1)),
        EpisodeEntity(
            2, "BrothersBirthday", "xxxxxxx xx, xxxx", "S01E02",
            mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        )
    )

//    fun fetchAllEpisodes(): LiveData<List<EpisodeEntity>> {
//        Log.d(TAG, "fetchAllEpisodes: ")
//        val responseEpisodesLiveData = MutableLiveData<List<EpisodeEntity>>()
//        val episodesListRequest: Call<EpisodesListResponse> = rickAndMortyApi.fetchEpisodes()
//        episodesListRequest.enqueue(object : Callback<EpisodesListResponse> {
//            override fun onResponse(
//                call: Call<EpisodesListResponse>,
//                response: Response<EpisodesListResponse>
//            ) {
//                Log.d(TAG, "Response received")
//                val episodesListResponse = response.body()
//                    ?: throw IllegalStateException("episodesListResponse body is null")
//                val episodesList = ArrayList<EpisodeEntity>()
//                episodesListResponse.episodesResponse!!.forEach {
//                    episodesList.add(it.parseToEpisodeEntity())
//                }
//                responseEpisodesLiveData.value = episodesList
//            }
//
//            override fun onFailure(call: Call<EpisodesListResponse>, t: Throwable) {
//                Log.e(TAG, "Failed to fetch episodes", t)
//            }
//
//        })
//        return responseEpisodesLiveData
//    }

    suspend fun fetchAllEpisodes(): List<EpisodeEntity>{
        val episodes = ArrayList<EpisodeEntity>()
        rickAndMortyApi.fetchEpisodes().episodesResponse.forEach{
            episodes.add(it.parseToEpisodeEntity())
        }
        return episodes
    }

    fun getAllEpisodes(): LiveData<List<EpisodeEntity>> {
        val episodesLiveData: MutableLiveData<List<EpisodeEntity>> = MutableLiveData()
        episodesLiveData.value = ArrayList(episodes)
        return episodesLiveData
    }

    fun getEpisode(id: Int): LiveData<EpisodeEntity?> {
        val episodeEntity = episodes[id - 1].copy()
        return MutableLiveData(episodeEntity)
    }

    fun getMultipleEpisodes(episodesIdList: List<Int>): LiveData<List<EpisodeEntity>> {
        val episodesLiveData: MutableLiveData<List<EpisodeEntity>> = MutableLiveData()
        val multipleEpisodes = ArrayList<EpisodeEntity>()
        episodesIdList.forEach {
            multipleEpisodes.add(episodes[it - 1].copy())
        }
        episodesLiveData.value = multipleEpisodes
        return episodesLiveData
    }

    companion object {
        private var INSTANCE: EpisodesRepository? = null

        fun initialize(appContext: Context, rickAndMortyApi: RickAndMortyApi) {
            INSTANCE = EpisodesRepository(rickAndMortyApi)
        }

        fun getInstance(): EpisodesRepository {
            return INSTANCE
                ?: throw IllegalStateException("Episodes repository must be initialized")
        }
    }
}