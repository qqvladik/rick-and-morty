package by.mankevich.rickandmorty.domain.locations

import android.os.Parcelable
import by.mankevich.rickandmorty.domain.base.IEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    var id: Int,
    var name: String,
    var type: String,
    var dimension: String,
    var residents: List<Int>
) : Parcelable, IEntity
