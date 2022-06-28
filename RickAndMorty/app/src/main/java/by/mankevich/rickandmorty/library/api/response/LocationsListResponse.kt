package by.mankevich.rickandmorty.library.api.response

import android.os.Parcelable
import by.mankevich.rickandmorty.library.db.CharacterEntity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationsListResponse(
    @SerializedName("results")
    val locationsResponse: List<LocationFullResponse>
) : Parcelable