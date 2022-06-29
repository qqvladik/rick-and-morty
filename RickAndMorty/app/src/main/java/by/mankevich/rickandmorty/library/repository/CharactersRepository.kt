package by.mankevich.rickandmorty.library.repository

import android.content.Context
import by.mankevich.photogallery.api.RickAndMortyApi
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.library.db.entity.Location
import by.mankevich.rickandmorty.library.db.entity.parseToCharacterEntity

private const val TAG = "RAMCharactersRepository"

class CharactersRepository private constructor(
    private val rickAndMortyApi: RickAndMortyApi
) {

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

    suspend fun fetchAllCharacters(): List<CharacterEntity>{
        val characters = ArrayList<CharacterEntity>()
        rickAndMortyApi.fetchCharacters().charactersResponse.forEach{
            characters.add(it.parseToCharacterEntity())
        }
        return characters
    }

    suspend fun getAllCharacters(): List<CharacterEntity> {
        return ArrayList(characters)
    }

    suspend fun getCharacter(id: Int): CharacterEntity? {
        return characters[id - 1].copy()
    }

    suspend fun getMultipleCharacters(charactersIdList: List<Int>): List<CharacterEntity> {
//        val charactersLiveData: MutableLiveData<List<CharacterEntity>> = MutableLiveData()
        val multipleCharacters = ArrayList<CharacterEntity>()
        charactersIdList.forEach {
            multipleCharacters.add(characters[it - 1].copy())
        }
//        charactersLiveData.value = multipleCharacters
        return multipleCharacters//charactersLiveData
    }

    companion object {
        private var INSTANCE: CharactersRepository? = null

        fun initialize(appContext: Context, rickAndMortyApi: RickAndMortyApi) {
            INSTANCE = CharactersRepository(rickAndMortyApi)
        }

        fun getInstance(): CharactersRepository {
            return INSTANCE
                ?: throw IllegalStateException("Characters repository must be initialized")
        }
    }
}