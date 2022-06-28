package by.mankevich.rickandmorty.feature

import android.app.Application
import by.mankevich.photogallery.api.RickAndMortyApi
import by.mankevich.rickandmorty.library.repository.CharactersRepository
import by.mankevich.rickandmorty.library.repository.EpisodesRepository
import by.mankevich.rickandmorty.library.repository.LocationsRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RickAndMortyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        initRepositories()
    }

    private fun initRepositories(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val rickAndMortyApi = retrofit.create(RickAndMortyApi::class.java)

        CharactersRepository.initialize(this, rickAndMortyApi)
        EpisodesRepository.initialize(this, rickAndMortyApi)
        LocationsRepository.initialize(this, rickAndMortyApi)
    }
}