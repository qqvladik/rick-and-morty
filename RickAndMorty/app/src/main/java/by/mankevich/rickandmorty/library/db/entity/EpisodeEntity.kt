package by.mankevich.rickandmorty.library.db.entity

import android.os.Parcelable
import by.mankevich.rickandmorty.domain.base.IEntity
import by.mankevich.rickandmorty.library.api.response.EpisodeResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeEntity(
    var id: Int,
    var name: String,
    var airDate: String,
    var episode: String,
    var characters: List<Int>
): Parcelable, IEntity{

    fun getSeasonNum(): String{      //"S03E07"
        return String(episode.toCharArray(2, 3))
    }

    fun getEpisodeNum(): String{    //"S03E07"
        return String(episode.toCharArray(5, 6))
    }
}

fun EpisodeResponse.parseToEpisodeEntity(): EpisodeEntity {
    return EpisodeEntity(
        id = id ?: 0,
        name = name ?: UNDEFINED_VALUE,
        airDate = airDate ?: UNDEFINED_VALUE,
        episode = episode ?: UNDEFINED_VALUE,
        characters = characters?.map { url ->
            val index = url.lastIndexOf("/")
            url.substring(index + 1).toInt()
        } ?: listOf()
    )
}
