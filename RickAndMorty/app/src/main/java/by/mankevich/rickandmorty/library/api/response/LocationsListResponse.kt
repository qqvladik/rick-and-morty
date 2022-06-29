package by.mankevich.rickandmorty.library.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationsListResponse(
    @SerializedName("results")
    val locationsResponse: List<LocationFullResponse>
) : Parcelable