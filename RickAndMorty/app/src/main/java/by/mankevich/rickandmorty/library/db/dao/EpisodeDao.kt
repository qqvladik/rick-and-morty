package by.mankevich.rickandmorty.library.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity

@Dao
interface EpisodeDao {
    companion object {
        const val EPISODE_TABLE_NAME = "episode"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_EPISODE = "episode"
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListEpisodes(episodes: List<EpisodeEntity>)

//    @Query("SELECT * FROM $EPISODE_TABLE_NAME WHERE $COLUMN_NAME LIKE  '%' || :query || '%'")
//    suspend fun searchEpisodes(query: String): List<EpisodeEntity>

    @Query("SELECT * FROM $EPISODE_TABLE_NAME WHERE $COLUMN_ID=(:id)")
    suspend fun getEpisodeById(id: Int): EpisodeEntity

//    @Query("SELECT * FROM $EPISODE_TABLE_NAME WHERE $COLUMN_ID in (:ids)")
//    suspend fun getEpisodesByIds(ids: List<Int>): List<EpisodeEntity>

//    @Query("SELECT * FROM $EPISODE_TABLE_NAME")
//    fun getAllEpisodes(): Flow<List<EpisodeEntity>>

//    @Query("SELECT * FROM $EPISODE_TABLE_NAME " +
//            "WHERE $COLUMN_NAME LIKE '%' || (:name) || '%' " +
//            "AND $COLUMN_EPISODE LIKE '%' || (:episode) || '%' ")
//    suspend fun filterEpisodes(
//        name: String = "",
//        episode: String = ""
//    ): List<EpisodeEntity>
}