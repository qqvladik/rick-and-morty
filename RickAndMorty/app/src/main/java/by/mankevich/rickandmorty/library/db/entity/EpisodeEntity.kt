package by.mankevich.rickandmorty.library.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import by.mankevich.rickandmorty.domain.base.IEntity
import by.mankevich.rickandmorty.library.api.response.EpisodeResponse
import by.mankevich.rickandmorty.library.db.dao.EpisodeDao
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = EpisodeDao.EPISODE_TABLE_NAME)
data class EpisodeEntity(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "air_date") var airDate: String,
    @ColumnInfo(name = "episode") var episode: String,
    @ColumnInfo(name = "characters") var characters: List<Int>
) : Parcelable, IEntity {

    fun getSeasonNum(): String {      //"S03E07"
        return String(episode.toCharArray(2, 3))
    }

    fun getEpisodeNum(): String {    //"S03E07"
        return String(episode.toCharArray(4, 6))
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
