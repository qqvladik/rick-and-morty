package by.mankevich.rickandmorty.library.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.mankevich.rickandmorty.domain.base.IEntity
import by.mankevich.rickandmorty.library.api.response.CharacterResponse
import by.mankevich.rickandmorty.library.api.response.LocationResponse
import by.mankevich.rickandmorty.library.db.dao.CharacterDao
import kotlinx.parcelize.Parcelize

const val UNDEFINED_VALUE = "undefined"

@Parcelize
@Entity(tableName = CharacterDao.CHARACTER_TABLE_NAME)
data class CharacterEntity(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "status") var status: String,
    @ColumnInfo(name = "species") var species: String,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "gender") var gender: String,
    @ColumnInfo(name = "origin") var origin: Location,
    @ColumnInfo(name = "location") var location: Location,
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "episode") var episode: List<Int>
) : Parcelable, IEntity {

    fun getStatusAndSpecies(): String {
        return status.plus(" - ").plus(species)
    }
}

fun CharacterResponse.parseToCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        id = id ?: 0,
        name = name ?: UNDEFINED_VALUE,
        status = status ?: UNDEFINED_VALUE,
        species = species ?: UNDEFINED_VALUE,
        type = if (type == "" || type == " ") UNDEFINED_VALUE else type ?: UNDEFINED_VALUE,
        gender = gender ?: UNDEFINED_VALUE,
        origin = origin?.parseToLocation() ?: Location(0, UNDEFINED_VALUE),
        location = location?.parseToLocation() ?: Location(0, UNDEFINED_VALUE),
        image = image ?: UNDEFINED_VALUE,
        episode = episode?.map { url ->
            val index = url.lastIndexOf("/")
            url.substring(index + 1).toInt()
        } ?: listOf()
    )
}

@Parcelize
data class Location(
    var id: Int,
    var name: String
) : Parcelable

fun LocationResponse.parseToLocation(): Location {
    return Location(
        name = name ?: UNDEFINED_VALUE,
        id = if (name == "unknown") {
            0
        } else {
            try {
                url?.substring(url.lastIndexOf('/') + 1)?.toInt() ?: 0
            } catch (e: NumberFormatException) {
                0
            }
        }
    )
}
