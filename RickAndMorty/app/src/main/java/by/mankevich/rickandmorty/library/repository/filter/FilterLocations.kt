package by.mankevich.rickandmorty.library.repository.filter

import android.os.Parcelable
import by.mankevich.rickandmorty.library.base.BaseFilter
import by.mankevich.rickandmorty.library.db.entity.LocationEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterLocations(
    var type: String = "",
    var dimension: String = ""
): BaseFilter<LocationEntity>, Parcelable