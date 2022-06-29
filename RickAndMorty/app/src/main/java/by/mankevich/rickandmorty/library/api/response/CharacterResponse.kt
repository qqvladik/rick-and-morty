package by.mankevich.rickandmorty.library.api.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterResponse(
    val id: Int?,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: LocationResponse?,
    val location: LocationResponse?,
    val image: String?,
    val episode: List<String>?
) : Parcelable

@Parcelize
data class LocationResponse(
    val name: String?,
    val url: String?
) : Parcelable