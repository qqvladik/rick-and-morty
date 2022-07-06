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
    var gender: String = ""
) : BaseFilter<CharacterEntity>, Parcelable{

    companion object{
        val CHARACTERS_STATUS_LIST = listOf("any", "Alive", "Dead", "unknown")
        val CHARACTERS_SPECIES_LIST = listOf("any", "Alien", "Animal", "Cronenberg", "Disease", "Human",
            "Humanoid", "Mythological Creature", "Poopybutthole", "Robot", "unknown")
        val CHARACTERS_GENDER_LIST = listOf("any", "Female", "Genderless", "Male", "unknown")
    }
}
