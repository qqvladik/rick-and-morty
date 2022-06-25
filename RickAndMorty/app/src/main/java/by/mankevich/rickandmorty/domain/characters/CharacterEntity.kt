package by.mankevich.rickandmorty.domain.characters

import android.os.Parcelable
import by.mankevich.rickandmorty.domain.base.IEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterEntity(
    var id: Int,
    var name: String,
    var status: String,
    var species: String,
    var type: String,
    var gender: String,
    var origin: Location,
    var location: Location,
    var image: String,
    var episodes: List<Int>
) : Parcelable, IEntity{

    fun getStatusAndSpecies(): String{
        return status.plus(" - ").plus(species)
    }
}

@Parcelize
data class Location(
    var id: Int,
    var name: String
) : Parcelable
