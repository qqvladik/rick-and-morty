package by.mankevich.rickandmorty.library.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.mankevich.rickandmorty.library.db.converter.ListIntConverter
import by.mankevich.rickandmorty.library.db.converter.LocationConverter
import by.mankevich.rickandmorty.library.db.dao.CharacterDao
import by.mankevich.rickandmorty.library.db.dao.EpisodeDao
import by.mankevich.rickandmorty.library.db.dao.LocationDao
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity
import by.mankevich.rickandmorty.library.db.entity.LocationEntity

@TypeConverters(
    ListIntConverter::class,
    LocationConverter::class
)
@Database(
    entities = [
        CharacterEntity::class,
        EpisodeEntity::class,
        LocationEntity::class
    ],
    version = RickAndMortyDatabase.DATABASE_VERSION
)
abstract class RickAndMortyDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "ram_database"
    }

    abstract fun getCharacterDao(): CharacterDao
    abstract fun getEpisodeDao(): EpisodeDao
    abstract fun getLocationDao(): LocationDao
}