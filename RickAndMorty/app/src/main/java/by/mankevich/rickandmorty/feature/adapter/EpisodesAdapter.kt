package by.mankevich.rickandmorty.feature.adapter

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.library.db.entity.EpisodeEntity
import by.mankevich.rickandmorty.feature.base.BaseAdapter
import by.mankevich.rickandmorty.feature.base.BaseViewHolder

class EpisodesAdapter(
    episodes: List<EpisodeEntity>? = null,
    private var onItemClick: (EpisodeEntity) -> Unit
) :
    BaseAdapter<EpisodeEntity, EpisodesAdapter.EpisodeViewHolder>(
        episodes,
        DIFF_ITEM_CALLBACK
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder =
        EpisodeViewHolder(R.layout.item_episode, parent, onItemClick)


    inner class EpisodeViewHolder(
        resource: Int,
        parent: ViewGroup,
        onItemClick: (EpisodeEntity) -> Unit
    ) :
        BaseViewHolder<EpisodeEntity>(
            resource,
            parent, onItemClick
        ) {

        private val textName: TextView = itemView.findViewById(R.id.item_text_episode_name)
        private val textDate: TextView = itemView.findViewById(R.id.item_text_date)
        private val textSeason: TextView = itemView.findViewById(R.id.item_text_season)
        private val textEpisodeNum: TextView = itemView.findViewById(R.id.item_text_episode_num)

        override fun bindView(entity: EpisodeEntity) {
            textName.text = entity.id.toString().plus(" ").plus(entity.name)
            textDate.text = entity.airDate
            textSeason.text = entity.getSeasonNum()
            textEpisodeNum.text = entity.getEpisodeNum()
        }
    }

    companion object {
        val DIFF_ITEM_CALLBACK = object : DiffUtil.ItemCallback<EpisodeEntity>() {
            override fun areItemsTheSame(oldItem: EpisodeEntity, newItem: EpisodeEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: EpisodeEntity,
                newItem: EpisodeEntity
            ): Boolean =
                oldItem.name == newItem.name &&
                        oldItem.airDate == newItem.airDate &&
                        oldItem.episode == newItem.episode
        }
    }
}