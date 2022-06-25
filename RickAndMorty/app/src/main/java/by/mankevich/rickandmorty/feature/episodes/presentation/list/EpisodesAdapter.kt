package by.mankevich.rickandmorty.feature.episodes.presentation.list

import android.view.ViewGroup
import android.widget.TextView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.domain.episodes.EpisodeEntity
import by.mankevich.rickandmorty.feature.base.BaseAdapter
import by.mankevich.rickandmorty.feature.base.BaseViewHolder

class EpisodesAdapter(episodes: List<EpisodeEntity>,private var onItemClick: (EpisodeEntity) -> Unit) :
    BaseAdapter<EpisodeEntity, EpisodesAdapter.EpisodeViewHolder>(episodes) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder =
        EpisodeViewHolder(R.layout.item_episode, parent, onItemClick)


    inner class EpisodeViewHolder(resource: Int, parent: ViewGroup, onItemClick: (EpisodeEntity) -> Unit) :
        BaseViewHolder<EpisodeEntity>(
            resource,
            parent, onItemClick
        ) {

        private val textName: TextView = itemView.findViewById(R.id.item_text_episode_name)
        private val textDate: TextView = itemView.findViewById(R.id.item_text_date)
        private val textSeason: TextView = itemView.findViewById(R.id.item_text_season)
        private val textEpisodeNum: TextView = itemView.findViewById(R.id.item_text_episode_num)

        override fun bindView(entity: EpisodeEntity) {
            textName.text = entity.name
            textDate.text = entity.airDate
            textSeason.text = entity.getSeasonNum()
            textEpisodeNum.text = entity.getEpisodeNum()
        }
    }
}