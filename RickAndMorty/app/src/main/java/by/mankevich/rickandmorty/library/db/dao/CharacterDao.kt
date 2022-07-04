package by.mankevich.rickandmorty.library.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.library.db.entity.Location

@Dao
interface CharacterDao {
    companion object {
        const val CHARACTER_TABLE_NAME = "character"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_STATUS = "status"
        const val COLUMN_SPECIES = "species"
        const val COLUMN_TYPE = "type"
        const val COLUMN_GENDER = "gender"
        const val COLUMN_ORIGIN = "origin"
        const val COLUMN_LOCATION = "location"
        const val COLUMN_EPISODE = "episode"
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListCharacters(characters: List<CharacterEntity>)

    @Query("SELECT * FROM $CHARACTER_TABLE_NAME WHERE $COLUMN_ID=(:id)")//todo
    suspend fun getCharacterById(id: Int): CharacterEntity?
//
    @Query("SELECT * FROM $CHARACTER_TABLE_NAME WHERE $COLUMN_ID in (:ids)")
    suspend fun getCharactersByIds(ids: List<Int>): List<CharacterEntity>

    @Query("SELECT * FROM $CHARACTER_TABLE_NAME " +//todo add to lower case
            "WHERE $COLUMN_NAME LIKE '%' || (:name) || '%' " +
            "AND $COLUMN_STATUS LIKE '%' || (:status) || '%' " +
            "AND $COLUMN_SPECIES LIKE '%' || (:species) || '%' " +
            "AND $COLUMN_TYPE LIKE '%' || (:type) || '%' " +
            "AND $COLUMN_GENDER LIKE '%' || (:gender) || '%' " +
            "AND $COLUMN_ORIGIN LIKE '%' || (:origin) || '%' " +
            "AND $COLUMN_LOCATION LIKE '%' || (:location) || '%'" +
            "ORDER BY id ASC LIMIT (:limit) OFFSET (:offset)")
    suspend fun getCharacters(
        name: String = "",
        status: String = "",
        species: String = "",
        type: String = "",
        gender: String = "",
        origin: String = "",
        location: String = "",
        limit: Int = 20,
        offset: Int
    ): List<CharacterEntity>
}