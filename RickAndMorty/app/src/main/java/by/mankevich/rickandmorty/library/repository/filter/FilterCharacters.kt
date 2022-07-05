package by.mankevich.rickandmorty.library.repository.filter

import android.os.Parcelable
import by.mankevich.rickandmorty.library.base.BaseFilter
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterCharacters(
    var status: String = "",
    var species: String = "",
    var type: String = "",
    var gender: String = "",
    var origin: String = "",
    var location: String = ""
) : BaseFilter<CharacterEntity>, Parcelable
