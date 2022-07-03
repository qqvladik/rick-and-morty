package by.mankevich.rickandmorty.library.repository

import by.mankevich.photogallery.api.RickAndMortyApi
import by.mankevich.rickandmorty.library.db.RickAndMortyDatabase
import by.mankevich.rickandmorty.library.base.BaseRepository
import by.mankevich.rickandmorty.library.db.dao.EpisodeDao
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity
import by.mankevich.rickandmorty.library.db.entity.parseToEpisodeEntity

private const val TAG = "RAMEpisodesRepository"

class EpisodesRepository private constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    rickAndMortyDatabase: RickAndMortyDatabase
) : BaseRepository<EpisodeEntity> {

    private val episodeDao: EpisodeDao = rickAndMortyDatabase.getEpisodeDao()

    var isConnect: Boolean = true
    var isInsert: Boolean = true

//    suspend fun fetchAllEpisodes(): List<EpisodeEntity> {
//        val episodes = ArrayList<EpisodeEntity>()
//        rickAndMortyApi.fetchEpisodes().episodesResponse.forEach {
//            episodes.add(it.parseToEpisodeEntity())
//        }
//        return episodes
//    }

    override suspend fun fetchAllByIsConnect(limit: Int, page: Int): List<EpisodeEntity> {//todo add Filter
        var episodes = ArrayList<EpisodeEntity>()
        if(isConnect) {
            rickAndMortyApi.fetchEpisodes(page = page).episodesResponse.forEach {
                episodes.add(it.parseToEpisodeEntity())
            }
            if (isInsert) {
                insertListEpisodes(episodes)
            }
        }else{
            episodes = episodeDao.getEpisodes(limit = limit, offset = (page - 1) * limit) as ArrayList<EpisodeEntity>
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

    suspend fun getEpisode(id: Int): EpisodeEntity? {
        return episodeDao.getEpisodeById(id)
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