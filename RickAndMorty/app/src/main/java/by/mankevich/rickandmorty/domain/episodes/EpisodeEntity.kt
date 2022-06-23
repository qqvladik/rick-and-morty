package by.mankevich.rickandmorty.domain.episodes

import android.os.Parcelable
import by.mankevich.rickandmorty.domain.base.IEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EpisodeEntity(
    var id: Int,
    var name: String,
    var airDate: String,
    var episode: String,
    var characters: List<Int>
): Parcelable, IEntity{

    fun getSeasonNum(): String{      //"S03E07"
        return episode.toCharArray(2, 3).toString()
    }

    fun getEpisodeNum(): String{    //"S03E07"
        return episode.toCharArray(5, 6).toString()
    }
}
