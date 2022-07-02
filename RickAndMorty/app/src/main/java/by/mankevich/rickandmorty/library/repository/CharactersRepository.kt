package by.mankevich.rickandmorty.library.repository

import by.mankevich.photogallery.api.RickAndMortyApi
import by.mankevich.rickandmorty.library.db.RickAndMortyDatabase
import by.mankevich.rickandmorty.library.db.base.BaseRepository
import by.mankevich.rickandmorty.library.db.dao.CharacterDao
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.library.db.entity.Location
import by.mankevich.rickandmorty.library.db.entity.parseToCharacterEntity

private const val TAG = "RAMCharactersRepository"

class CharactersRepository private constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    rickAndMortyDatabase: RickAndMortyDatabase
) : BaseRepository {
    private val characterDao: CharacterDao = rickAndMortyDatabase.getCharacterDao()

    private var characters: List<CharacterEntity> = mutableListOf(
        CharacterEntity(
            1, "Vlad", "Alive", "Human", "Me",
            "Male", Location(1, "Earth"), Location(2, "Mars"),
            "https://rickandmortyapi.com/api/character/avatar/1.jpeg", mutableListOf(1, 2)
        ),
        CharacterEntity(
            2, "Eugene Mankevich Petrovich", "Alive", "Human", "Brother",
            "Male", Location(2, "Mars"), Location(1, "Earth"),
            "https://rickandmortyapi.com/api/character/avatar/2.jpeg", mutableListOf(2)
        ),
        CharacterEntity(
            3, "Vlad", "Alive", "Human", "Me",
            "Male", Location(1, "Earth"), Location(2, "Mars"),
            "https://picsum.photos/id/237/200/300", mutableListOf(1, 2)
        ),
        CharacterEntity(
            4, "Eugene Mankevich Petrovich", "Alive", "Human", "Brother",
            "Male", Location(2, "Mars"), Location(1, "Earth"),
            "https://rickandmortyapi.com/api/character/avatar/2.jpeg", mutableListOf(2)
        ),
        CharacterEntity(
            5, "Vlad", "Alive", "Human", "Me",
            "Male", Location(1, "Earth"), Location(2, "Mars"),
            "https://picsum.photos/id/237/200/300", mutableListOf(1, 2)
        ),
        CharacterEntity(
            6, "Eugene Mankevich Petrovich", "Alive", "Human", "Brother",
            "Male", Location(2, "Mars"), Location(1, "Earth"),
            "https://rickandmortyapi.com/api/character/avatar/2.jpeg", mutableListOf(2)
        ),
        CharacterEntity(
            7, "Vlad", "Alive", "Human", "Me",
            "Male", Location(1, "Earth"), Location(2, "Mars"),
            "https://picsum.photos/id/237/200/300", mutableListOf(1, 2)
        ),
        CharacterEntity(
            8, "Eugene Mankevich Petrovich", "Alive", "Human", "Brother",
            "Male", Location(2, "Mars"), Location(1, "Earth"),
            "https://rickandmortyapi.com/api/character/avatar/2.jpeg", mutableListOf(2)
        ),
        CharacterEntity(
            9, "Vlad", "Alive", "Human", "Me",
            "Male", Location(1, "Earth"), Location(2, "Mars"),
            "https://picsum.photos/id/237/200/300", mutableListOf(1, 2)
        ),
        CharacterEntity(
            10, "Eugene Mankevich Petrovich", "Alive", "Human", "Brother",
            "Male", Location(2, "Mars"), Location(1, "Earth"),
            "https://rickandmortyapi.com/api/character/avatar/2.jpeg", mutableListOf(2)
        ),
        CharacterEntity(
            11, "Vlad", "Alive", "Human", "Me",
            "Male", Location(1, "Earth"), Location(2, "Mars"),
            "https://picsum.photos/id/237/200/300", mutableListOf(1, 2)
        ),
        CharacterEntity(
            12, "Eugene Mankevich Petrovich", "Alive", "Human", "Brother",
            "Male", Location(2, "Mars"), Location(1, "Earth"),
            "https://rickandmortyapi.com/api/character/avatar/2.jpeg", mutableListOf(2)
        ),
    )

    suspend fun fetchAllCharacters(): List<CharacterEntity> {
        val characters = ArrayList<CharacterEntity>()
        rickAndMortyApi.fetchCharacters().charactersResponse.forEach {
            characters.add(it.parseToCharacterEntity())
        }
        return characters
    }

    suspend fun fetchAllAndInsertCharacters(): List<CharacterEntity> {
        val characters = ArrayList<CharacterEntity>()
        rickAndMortyApi.fetchCharacters().charactersResponse.forEach {
            characters.add(it.parseToCharacterEntity())
        }
        insertListCharacters(characters)
        return characters
    }

    suspend fun fetchMultipleCharacters(ids: List<Int>): List<CharacterEntity> {
        val characters = ArrayList<CharacterEntity>()
        if (validateIds(ids)) {
            rickAndMortyApi.fetchMultipleCharacters(ids).forEach {
                characters.add(it.parseToCharacterEntity())
            }
        }
        return characters
    }

    suspend fun fetchMultipleAndInsertCharacters(ids: List<Int>): List<CharacterEntity> {
        val characters = ArrayList<CharacterEntity>()
        if (validateIds(ids)) {
            rickAndMortyApi.fetchMultipleCharacters(ids).forEach {
                characters.add(it.parseToCharacterEntity())
            }
            insertListCharacters(characters)
        }
        return characters
    }

    suspend fun insertListCharacters(characters: List<CharacterEntity>) {
        characterDao.insertListCharacters(characters)
    }

    suspend fun getAllCharacters(): List<CharacterEntity> {//todo
        return ArrayList(characters)
    }

    suspend fun getCharacter(id: Int): CharacterEntity? {
        return characterDao.getCharacterById(id)
    }

    suspend fun getMultipleCharacters(charactersIdList: List<Int>): List<CharacterEntity> {//todo
        val multipleCharacters = ArrayList<CharacterEntity>()
        charactersIdList.forEach {
            multipleCharacters.add(characters[it - 1].copy())
        }
        return multipleCharacters//charactersLiveData
    }

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