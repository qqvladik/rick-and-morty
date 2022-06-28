package by.mankevich.rickandmorty.library.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharactersListResponse(
    @SerializedName("results") val charactersResponse: List<CharacterResponse>
) : Parcelable