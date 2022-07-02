package by.mankevich.rickandmorty.library.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity
import by.mankevich.rickandmorty.library.db.entity.LocationEntity

@Dao
interface LocationDao {
    companion object {
        const val LOCATION_TABLE_NAME = "location"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_TYPE = "type"
        const val COLUMN_DIMENSION = "dimension"
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListLocations(locations: List<LocationEntity>)
//
//    @Query("SELECT * FROM $LOCATION_TABLE_NAME WHERE $COLUMN_NAME LIKE  '%' || :query || '%'")
//    suspend fun searchLocations(query: String): List<LocationEntity>
//
    @Query("SELECT * FROM $LOCATION_TABLE_NAME WHERE $COLUMN_ID=(:id)")
    suspend fun getLocationById(id: Int): LocationEntity

//    @Query("SELECT * FROM $LOCATION_TABLE_NAME WHERE $COLUMN_ID in (:ids)")
//    suspend fun getLocationsByIds(ids: List<Int>): List<LocationEntity>

//    @Query("SELECT * FROM $LOCATION_TABLE_NAME")
//    fun getAllLocations(): Flow<List<LocationEntity>>

//    @Query("SELECT * FROM $LOCATION_TABLE_NAME " +
//            "WHERE $COLUMN_NAME LIKE '%' || (:name) || '%' " +
//            "AND $COLUMN_TYPE LIKE '%' || (:type) || '%' " +
//            "AND $COLUMN_DIMENSION LIKE '%' || (:dimension) || '%' ")
//    suspend fun filterEpisodes(
//        name: String = "",
//        type: String = "",
//        dimension: String = ""
//    ): List<EpisodeEntity>
}