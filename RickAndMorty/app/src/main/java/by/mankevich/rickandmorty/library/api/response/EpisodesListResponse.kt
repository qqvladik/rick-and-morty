package by.mankevich.rickandmorty.library.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodesListResponse(
    @SerializedName("results") val episodesResponse: List<EpisodeResponse>
) : Parcelable