package by.mankevich.rickandmorty.library.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.mankevich.rickandmorty.domain.characters.CharacterEntity
import by.mankevich.rickandmorty.domain.characters.Location
import by.mankevich.rickandmorty.domain.episodes.EpisodeEntity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharactersRepository private constructor() {

    //private val rickAndMortyApi: RickAndMortyApi
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

    init {
        /*val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        rickAndMortyApi = retrofit.create(RickAndMortyApi::class.java)*/

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
        fun getInstance(): CharactersRepository {
            return CharactersRepository()
        }
    }
}