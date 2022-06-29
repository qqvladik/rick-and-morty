package by.mankevich.rickandmorty.library.api.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationFullResponse(
    val id: Int?,
    val name: String?,
    val type: String?,
    val dimension: String?,
    val residents: List<String>?
) : Parcelable