package by.mankevich.rickandmorty.library.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.mankevich.rickandmorty.domain.base.IEntity
import by.mankevich.rickandmorty.library.api.response.LocationFullResponse
import by.mankevich.rickandmorty.library.db.dao.LocationDao
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = LocationDao.LOCATION_TABLE_NAME)
data class LocationEntity(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "dimension") var dimension: String,
    @ColumnInfo(name = "residents") var residents: List<Int>
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
