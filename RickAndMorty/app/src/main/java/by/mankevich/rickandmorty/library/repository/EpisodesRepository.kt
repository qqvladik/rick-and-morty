package by.mankevich.rickandmorty.library.repository

import by.mankevich.photogallery.api.RickAndMortyApi
import by.mankevich.rickandmorty.library.base.BaseFilter
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

    override suspend fun fetchAllByIsConnect(
        limit: Int,
        page: Int,
        search: String,
//        filter: BaseFilter<EpisodeEntity>
    ): List<EpisodeEntity> {//todo add Filter
        val episodes: List<EpisodeEntity>//()
        if (isConnect) {
            episodes = fetchAllEpisodes(page, search)
            if (isInsert) {
                insertListEpisodes(episodes)
            }
        } else {
            episodes = getEpisodes(limit, page, search)
        }
        return episodes
    }

    suspend fun fetchMultipleEpisodesByIsConnect(ids: List<Int>): List<EpisodeEntity> {
        val episodes: List<EpisodeEntity>
        if (validateIds(ids)) {
            if (isConnect) {
                episodes = fetchMultipleEpisodes(ids)
                if (isInsert) {
                    insertListEpisodes(episodes)
                }
            } else {
                episodes = getMultipleEpisodes(ids)
            }
        } else {
            return emptyList()
        }
        return episodes
    }

    suspend fun fetchAllEpisodes(
        page: Int,
        search: String,
    ): List<EpisodeEntity> {
        val episodes = ArrayList<EpisodeEntity>()
        rickAndMortyApi.fetchEpisodes(
            page = page,
            name = search
        ).episodesResponse.forEach {
            episodes.add(it.parseToEpisodeEntity())
        }
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

    suspend fun insertListEpisodes(episodes: List<EpisodeEntity>) =
        episodeDao.insertListEpisodes(episodes)

    suspend fun getEpisode(id: Int): EpisodeEntity? = episodeDao.getEpisodeById(id)

    suspend fun getEpisodes(
        limit: Int,
        page: Int,
        search: String
    ): List<EpisodeEntity> = episodeDao.getEpisodes(
            limit = limit,
            offset = (page - 1) * limit,
            name = search
        )

    suspend fun getMultipleEpisodes(ids: List<Int>) = episodeDao.getEpisodesByIds(ids)

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