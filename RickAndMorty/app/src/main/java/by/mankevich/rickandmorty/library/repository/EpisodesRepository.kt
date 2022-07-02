package by.mankevich.rickandmorty.library.repository

import by.mankevich.photogallery.api.RickAndMortyApi
import by.mankevich.rickandmorty.library.db.RickAndMortyDatabase
import by.mankevich.rickandmorty.library.db.base.BaseRepository
import by.mankevich.rickandmorty.library.db.dao.EpisodeDao
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity
import by.mankevich.rickandmorty.library.db.entity.parseToEpisodeEntity

private const val TAG = "RAMEpisodesRepository"

class EpisodesRepository private constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    rickAndMortyDatabase: RickAndMortyDatabase
) : BaseRepository {

    private val episodeDao: EpisodeDao = rickAndMortyDatabase.getEpisodeDao()

    private var episodes: List<EpisodeEntity> = mutableListOf(
        EpisodeEntity(1, "Birthday", "October 09, 1998", "S01E01", mutableListOf(1)),
        EpisodeEntity(
            2, "BrothersBirthday", "xxxxxxx xx, xxxx", "S01E02",
            mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        )
    )

    suspend fun fetchAllEpisodes(): List<EpisodeEntity> {
        val episodes = ArrayList<EpisodeEntity>()
        rickAndMortyApi.fetchEpisodes().episodesResponse.forEach {
            episodes.add(it.parseToEpisodeEntity())
        }
        return episodes
    }

    suspend fun fetchAllAndInsertEpisodes(): List<EpisodeEntity> {
        val episodes = ArrayList<EpisodeEntity>()
        rickAndMortyApi.fetchEpisodes().episodesResponse.forEach {
            episodes.add(it.parseToEpisodeEntity())
        }
        insertListEpisodes(episodes)
        return episodes
    }

    suspend fun fetchMultipleEpisodes(ids: List<Int>): List<EpisodeEntity> {
        val episodes = ArrayList<EpisodeEntity>()
        if (validateIds(ids)) {
            rickAndMortyApi.fetchMultipleEpisodes(ids).forEach {
                episodes.add(it.parseToEpisodeEntity())
            }
        }
        return episodes
    }

    suspend fun fetchMultipleAndInsertEpisodes(ids: List<Int>): List<EpisodeEntity> {
        val episodes = ArrayList<EpisodeEntity>()
        if (validateIds(ids)) {
            rickAndMortyApi.fetchMultipleEpisodes(ids).forEach {
                episodes.add(it.parseToEpisodeEntity())
            }
            insertListEpisodes(episodes)
        }
        return episodes
    }

    suspend fun insertListEpisodes(episodes: List<EpisodeEntity>) {
        episodeDao.insertListEpisodes(episodes)
    }

    suspend fun getAllEpisodes(): List<EpisodeEntity> {//todo
        return ArrayList(episodes)
    }

    suspend fun getEpisode(id: Int): EpisodeEntity? {
        return episodeDao.getEpisodeById(id)
    }

    suspend fun getMultipleEpisodes(episodesIdList: List<Int>): List<EpisodeEntity> {//todo
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

        fun initialize(
            rickAndMortyApi: RickAndMortyApi,
            rickAndMortyDatabase: RickAndMortyDatabase
        ) {
            INSTANCE = EpisodesRepository(rickAndMortyApi, rickAndMortyDatabase)
        }

        fun getInstance(): EpisodesRepository {
            return INSTANCE
                ?: throw IllegalStateException("Episodes repository must be initialized")
        }
    }
}