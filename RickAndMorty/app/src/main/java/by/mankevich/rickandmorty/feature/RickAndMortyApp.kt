package by.mankevich.rickandmorty.feature

import android.app.Application
import androidx.room.Room
import by.mankevich.photogallery.api.RickAndMortyApi
import by.mankevich.rickandmorty.library.db.RickAndMortyDatabase
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

        val database: RickAndMortyDatabase = Room.databaseBuilder(
            this.applicationContext,
            RickAndMortyDatabase::class.java,
            RickAndMortyDatabase.DATABASE_NAME
        ).build()

        CharactersRepository.initialize(rickAndMortyApi, database)
        EpisodesRepository.initialize(rickAndMortyApi, database)
        LocationsRepository.initialize(rickAndMortyApi, database)
    }
}