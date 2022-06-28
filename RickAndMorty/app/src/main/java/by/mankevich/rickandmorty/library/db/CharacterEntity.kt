package by.mankevich.rickandmorty.library.db

import android.os.Parcelable
import by.mankevich.rickandmorty.domain.base.IEntity
import by.mankevich.rickandmorty.library.api.response.CharacterResponse
import by.mankevich.rickandmorty.library.api.response.LocationResponse
import kotlinx.android.parcel.Parcelize

const val UNDEFINED_VALUE = "undefined"

@Parcelize
data class CharacterEntity(
    var id: Int,
    var name: String,
    var status: String,
    var species: String,
    var type: String,
    var gender: String,
    var origin: Location,
    var location: Location,
    var image: String,
    var episode: List<Int>
) : Parcelable, IEntity{

    fun getStatusAndSpecies(): String{
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
        name = name?: UNDEFINED_VALUE,
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
