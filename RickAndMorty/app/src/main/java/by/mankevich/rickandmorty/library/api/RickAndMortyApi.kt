package by.mankevich.photogallery.api

import by.mankevich.rickandmorty.library.api.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun fetchCharacters(
        @Query("page") page: Int = 1,
        @Query("name") name: String = "",
        @Query("status") status: String = "",
        @Query("species") species: String = "",
        @Query("type") type: String = "",
        @Query("gender") gender: String = ""
    ): CharactersListResponse

    @GET("location")
    suspend fun fetchLocations(
        @Query("page") page: Int = 1,
        @Query("name") name: String = "",
        @Query("type") type: String = "",
        @Query("dimension") dimension: String = ""
    ): LocationsListResponse

    @GET("episode")
    suspend fun fetchEpisodes(
        @Query("page") page: Int = 1,
        @Query("name") name: String = "",
        @Query("episode") episode: String = ""
    ): EpisodesListResponse

    @GET("character/{ids}")
    suspend fun fetchMultipleCharacters(@Path("ids") ids: List<Int>): List<CharacterResponse>

//    @GET("location/{ids}")
//    suspend fun fetchMultipleLocations(@Path("ids") ids: List<Int>): List<LocationResponse>

    @GET("episode/{ids}")
    suspend fun fetchMultipleEpisodes(@Path("ids") ids: List<Int>): List<EpisodeResponse>
}