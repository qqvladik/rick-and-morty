package by.mankevich.rickandmorty.library.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.mankevich.photogallery.api.RickAndMortyApi
import by.mankevich.rickandmorty.library.db.*

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

//    fun fetchAllCharacters(): LiveData<List<CharacterEntity>> {
//        Log.d(TAG, "fetchAllCharacters: ")
//        val responseCharactersLiveData = MutableLiveData<List<CharacterEntity>>()
//        val charactersListRequest: Call<CharactersListResponse> = rickAndMortyApi.fetchCharacters()
//        charactersListRequest.enqueue(object : Callback<CharactersListResponse> {
//            override fun onResponse(
//                call: Call<CharactersListResponse>,
//                response: Response<CharactersListResponse>
//            ) {
//                Log.d(TAG, "Response received")
//                val charactersListResponse = response.body()
//                    ?: throw IllegalStateException("charactersListResponse body is null")
//                val charactersList = ArrayList<CharacterEntity>()
//                charactersListResponse.charactersResponse!!.forEach {
//                    charactersList.add(it.parseToCharacterEntity())
//                }
//                responseCharactersLiveData.value = charactersList
//            }
//
//            override fun onFailure(call: Call<CharactersListResponse>, t: Throwable) {
//                Log.e(TAG, "Failed to fetch characters", t)
//            }
//
//        })
//        return responseCharactersLiveData
//    }

    suspend fun fetchAllCharacters(): List<CharacterEntity>{
        val characters = ArrayList<CharacterEntity>()
        rickAndMortyApi.fetchCharacters().charactersResponse.forEach{
            characters.add(it.parseToCharacterEntity())
        }
        return characters
    }

    fun getAllCharacters(): LiveData<List<CharacterEntity>> {
        val charactersLiveData: MutableLiveData<List<CharacterEntity>> = MutableLiveData()
        charactersLiveData.value = ArrayList(characters)
        return charactersLiveData
    }

    fun getCharacter(id: Int): LiveData<CharacterEntity?> {
        val characterCopy = characters[id - 1].copy()
        return MutableLiveData(characterCopy)
    }

    fun getMultipleCharacters(charactersIdList: List<Int>): LiveData<List<CharacterEntity>> {
        val charactersLiveData: MutableLiveData<List<CharacterEntity>> = MutableLiveData()
        val multipleCharacters = ArrayList<CharacterEntity>()
        charactersIdList.forEach {
            multipleCharacters.add(characters[it - 1].copy())
        }
        charactersLiveData.value = multipleCharacters
        return charactersLiveData
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