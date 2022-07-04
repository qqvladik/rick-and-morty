package by.mankevich.rickandmorty.library.repository

import by.mankevich.photogallery.api.RickAndMortyApi
import by.mankevich.rickandmorty.library.db.RickAndMortyDatabase
import by.mankevich.rickandmorty.library.base.BaseRepository
import by.mankevich.rickandmorty.library.db.dao.CharacterDao
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.library.db.entity.parseToCharacterEntity

private const val TAG = "RAMCharactersRepository"

class CharactersRepository private constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    rickAndMortyDatabase: RickAndMortyDatabase
) : BaseRepository<CharacterEntity> {
    private val characterDao: CharacterDao = rickAndMortyDatabase.getCharacterDao()

    var isConnect: Boolean = true
    var isInsert: Boolean = true

    override suspend fun fetchAllByIsConnect(
        limit: Int,
        page: Int
    ): List<CharacterEntity> {//todo add Filter
        val characters: List<CharacterEntity>//()
        if (isConnect) {
//            rickAndMortyApi.fetchCharacters(page = page).charactersResponse.forEach {
//                characters.add(it.parseToCharacterEntity())
//            }
            characters = fetchAllCharacters(page)
            if (isInsert) {
                insertListCharacters(characters)
            }
        } else {
//            characters = characterDao.getCharacters(
//                limit = limit,
//                offset = (page - 1) * limit
//            )
            characters = getCharacters(limit, page)
        }
        return characters
    }

    suspend fun fetchMultipleCharactersByIsConnect(ids: List<Int>): List<CharacterEntity> {//todo add isInsert and isConnect
        val characters: List<CharacterEntity>//()
        if (validateIds(ids)) {
            if (isConnect) {
//                rickAndMortyApi.fetchMultipleCharacters(ids).forEach {
//                    characters.add(it.parseToCharacterEntity())
//                }
                characters = fetchMultipleCharacters(ids)
                if (isInsert) {
                    insertListCharacters(characters)
                }
            } else {
                characters = getMultipleCharacters(ids)
            }
        }else{
            return emptyList()
        }
        return characters
    }

    suspend fun fetchAllCharacters(page: Int): List<CharacterEntity> {
        val characters = ArrayList<CharacterEntity>()
        rickAndMortyApi.fetchCharacters(page = page).charactersResponse.forEach {
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

    suspend fun getCharacters(limit: Int, page: Int): List<CharacterEntity> =
        characterDao.getCharacters(limit = limit, offset = (page - 1) * limit)

    suspend fun getMultipleCharacters(ids: List<Int>) = characterDao.getCharactersByIds(ids)

    companion object {
        private var INSTANCE: CharactersRepository? = null

        fun initialize(
            rickAndMortyApi: RickAndMortyApi,
            rickAndMortyDatabase: RickAndMortyDatabase
        ) {
            INSTANCE = CharactersRepository(rickAndMortyApi, rickAndMortyDatabase)
        }

        fun getInstance(): CharactersRepository {
            return INSTANCE
                ?: throw IllegalStateException("Characters repository must be initialized")
        }
    }
}