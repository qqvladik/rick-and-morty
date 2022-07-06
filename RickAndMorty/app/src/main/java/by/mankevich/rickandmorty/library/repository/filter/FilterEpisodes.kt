package by.mankevich.rickandmorty.library.repository.filter

import android.os.Parcelable
import by.mankevich.rickandmorty.library.base.BaseFilter
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterEpisodes (
    var season: String = "",
    var episodeNum: String = ""
): BaseFilter<EpisodeEntity>, Parcelable{

    fun getEpisodeForQuery(): String {
        var season = ""
        if(this.season!=""){
            season = "S0".plus(this.season)
        }
        return season.plus("E").plus(episodeNum)
    }

    companion object{
        val SEASONS_LIST = listOf("any", "1", "2", "3", "4", "5")
    }
}