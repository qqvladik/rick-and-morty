package by.mankevich.rickandmorty.library.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.mankevich.rickandmorty.domain.characters.CharacterEntity
import by.mankevich.rickandmorty.domain.characters.Location
import by.mankevich.rickandmorty.domain.episodes.EpisodeEntity

class EpisodesRepository private constructor() {

    //private val rickAndMortyApi: RickAndMortyApi
    private var episodes: List<EpisodeEntity> = mutableListOf(
        EpisodeEntity(1, "Birthday", "October 09, 1998", "S01E01", mutableListOf(1)),
        EpisodeEntity(2, "BrothersBirthday", "xxxxxxx xx, xxxx", "S01E02", mutableListOf(1, 2))
    )

    init {
        /*val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        rickAndMortyApi = retrofit.create(RickAndMortyApi::class.java)*/

    }

    fun getAllEpisodes(): LiveData<List<EpisodeEntity>> {
        val episodesLiveData: MutableLiveData<List<EpisodeEntity>> = MutableLiveData()
        episodesLiveData.value = ArrayList(episodes)
        return episodesLiveData
    }

    fun getEpisode(id: Int): LiveData<EpisodeEntity?>{
        val episodeEntity = episodes[id-1].copy()
        return MutableLiveData(episodeEntity)
    }

    fun getMultipleEpisodes(episodesIdList: List<Int>): LiveData<List<EpisodeEntity>>{
        val episodesLiveData: MutableLiveData<List<EpisodeEntity>> = MutableLiveData()
        val multipleEpisodes = ArrayList<EpisodeEntity>()
        episodesIdList.forEach{
            multipleEpisodes.add(episodes[it-1].copy())
        }
        episodesLiveData.value = multipleEpisodes
        return episodesLiveData
    }

    companion object{
        fun getInstance(): EpisodesRepository{
            return EpisodesRepository()
        }
    }
}