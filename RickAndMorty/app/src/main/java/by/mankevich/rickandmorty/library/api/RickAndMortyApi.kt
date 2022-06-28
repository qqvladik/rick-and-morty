package by.mankevich.photogallery.api

import by.mankevich.rickandmorty.library.api.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyApi {

    @GET("character")
    suspend fun fetchCharacters(): CharactersListResponse

    @GET("character/{id}")
    fun fetchCharacterById(@Path("id") id: Int): CharacterResponse

    @GET("location")
    suspend fun fetchLocations(): LocationsListResponse

    @GET("episode")
    suspend fun fetchEpisodes(): EpisodesListResponse

}