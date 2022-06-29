package by.mankevich.rickandmorty.library.db.entity

import android.os.Parcelable
import by.mankevich.rickandmorty.domain.base.IEntity
import by.mankevich.rickandmorty.library.api.response.LocationFullResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationEntity(
    var id: Int,
    var name: String,
    var type: String,
    var dimension: String,
    var residents: List<Int>
) : Parcelable, IEntity

fun LocationFullResponse.parseToLocationEntity(): LocationEntity {
    return LocationEntity(
        id = id ?: 0,
        name = name ?: UNDEFINED_VALUE,
        type = type ?: UNDEFINED_VALUE,
        dimension = dimension ?: UNDEFINED_VALUE,
        residents = residents?.map { url ->
            val index = url.lastIndexOf("/")
            url.substring(index + 1).toInt()
        } ?: listOf()
    )
}
