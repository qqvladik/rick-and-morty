package by.mankevich.rickandmorty.library.repository

import android.content.Context
import by.mankevich.photogallery.api.RickAndMortyApi
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity
import by.mankevich.rickandmorty.library.db.entity.parseToEpisodeEntity

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

    suspend fun fetchAllEpisodes(): List<EpisodeEntity>{
        val episodes = ArrayList<EpisodeEntity>()
        rickAndMortyApi.fetchEpisodes().episodesResponse.forEach{
            episodes.add(it.parseToEpisodeEntity())
        }
        return episodes
    }

    suspend fun getAllEpisodes(): List<EpisodeEntity> {
        return ArrayList(episodes)
    }

    suspend fun getEpisode(id: Int): EpisodeEntity? {
        return episodes[id - 1].copy()
    }

    suspend fun getMultipleEpisodes(episodesIdList: List<Int>): List<EpisodeEntity> {
//        val episodesLiveData: MutableLiveData<List<EpisodeEntity>> = MutableLiveData()
        val multipleEpisodes = ArrayList<EpisodeEntity>()
        episodesIdList.forEach {
            multipleEpisodes.add(episodes[it - 1].copy())
        }
//        episodesLiveData.value = multipleEpisodes
        return multipleEpisodes//episodesLiveData
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