package by.mankevich.rickandmorty.library.repository

import by.mankevich.photogallery.api.RickAndMortyApi
import by.mankevich.rickandmorty.library.base.BaseFilter
import by.mankevich.rickandmorty.library.db.RickAndMortyDatabase
import by.mankevich.rickandmorty.library.base.BaseRepository
import by.mankevich.rickandmorty.library.db.dao.CharacterDao
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.library.db.entity.parseToCharacterEntity
import by.mankevich.rickandmorty.library.repository.filter.FilterCharacters

private const val TAG = "RAMCharactersRepository"

class CharactersRepository private constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    rickAndMortyDatabase: RickAndMortyDatabase,
//    val filter: FilterCharacters = FilterCharacters()
) : BaseRepository<CharacterEntity> {
    private val characterDao: CharacterDao = rickAndMortyDatabase.getCharacterDao()

    var isConnect: Boolean = true
    var isInsert: Boolean = true

    override suspend fun fetchAllByIsConnect(
        limit: Int,
        page: Int,
        filter: BaseFilter<CharacterEntity>
    ): List<CharacterEntity> {
        val characters: List<CharacterEntity>
        if (isConnect) {
            characters = fetchAllCharacters(page, filter as FilterCharacters)//todo add Filter
            if (isInsert) {
                insertListCharacters(characters)
            }
        } else {
            characters = getCharacters(limit, page, filter as FilterCharacters)
        }
        return characters
    }

    suspend fun fetchMultipleCharactersByIsConnect(ids: List<Int>): List<CharacterEntity> {
        val characters: List<CharacterEntity>
        if (validateIds(ids)) {
            if (isConnect) {
                characters = fetchMultipleCharacters(ids)
                if (isInsert) {
                    insertListCharacters(characters)
                }
            } else {
                characters = getMultipleCharacters(ids)
            }
        } else {
            return emptyList()
        }
        return characters
    }

    suspend fun fetchAllCharacters(
        page: Int,
        filter: FilterCharacters = FilterCharacters("morty", "dead")//todo remove
    ): List<CharacterEntity> {//todo add Filter
        val characters = ArrayList<CharacterEntity>()
        rickAndMortyApi.fetchCharacters(
            page = page,
            name = filter.name,
            status = filter.status,
            species = filter.species,
            type = filter.type,
            gender = filter.gender
        ).charactersResponse.forEach {
            characters.add(it.parseToCharacterEntity())
        }
        return characters
    }

    suspend fun fetchMultipleCharacters(ids: List<Int>): List<CharacterEntity> {
        val characters = ArrayList<CharacterEntity>()
        rickAndMortyApi.fetchMultipleCharacters(ids).forEach {
            characters.add(it.parseToCharacterEntity())
        }
        return characters
    }

    suspend fun insertListCharacters(characters: List<CharacterEntity>) =
        characterDao.insertListCharacters(characters)

    suspend fun getCharacter(id: Int): CharacterEntity? = characterDao.getCharacterById(id)

    suspend fun getCharacters(
        limit: Int,
        page: Int,
        filter: FilterCharacters// = FilterCharacters(species = "alien")//todo remove
    ): List<CharacterEntity> =
        characterDao.getCharacters(
            limit = limit,
            offset = (page - 1) * limit,
            name = filter.name,
            status = filter.status,
            species = filter.species,
            type = filter.type,
            gender = filter.gender
        )

    suspend fun getMultipleCharacters(ids: List<Int>) = characterDao.getCharactersByIds(ids)

    companion object {
        private var INSTANCE: CharactersRepository? = null

        fun initialize(
            rickAndMortyApi: RickAndMortyApi,
            rickAndMortyDatabase: RickAndMortyDatabase
        ) {
            INSTANCE =
                CharactersRepository(rickAndMortyApi, rickAndMortyDatabase/*, FilterCharacters()*/)
        }

        fun getInstance(): CharactersRepository {
            return INSTANCE
                ?: throw IllegalStateException("Characters repository must be initialized")
        }
    }
}