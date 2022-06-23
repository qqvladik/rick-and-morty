package by.mankevich.rickandmorty.feature.episodes.presentation.list

import by.mankevich.rickandmorty.domain.episodes.EpisodeEntity
import by.mankevich.rickandmorty.feature.base.BaseDiffUtilCallback

class EpisodesDiffUtilCallback(oldList: List<EpisodeEntity>,
                               newList: List<EpisodeEntity>
) : BaseDiffUtilCallback<EpisodeEntity>(oldList, newList) {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name &&
                oldList[oldItemPosition].airDate == newList[newItemPosition].airDate &&
                oldList[oldItemPosition].episode == newList[newItemPosition].episode
    }
}