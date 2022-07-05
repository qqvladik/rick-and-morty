package by.mankevich.rickandmorty.library.repository.filter

import android.os.Parcelable
import by.mankevich.rickandmorty.library.base.BaseFilter
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterEpisodes (
    var episode: String = ""
): BaseFilter<EpisodeEntity>, Parcelable