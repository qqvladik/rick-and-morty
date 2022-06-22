package by.mankevich.rickandmorty.feature.episodes.presentation.list

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.domain.characters.Character
import by.mankevich.rickandmorty.domain.episodes.Episode
import by.mankevich.rickandmorty.feature.base.BaseAdapter
import by.mankevich.rickandmorty.feature.base.BaseViewHolder
import com.squareup.picasso.Picasso

class EpisodesAdapter(episodes: List<Episode>) :
    BaseAdapter<Episode, EpisodesAdapter.EpisodeViewHolder>(episodes) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder =
        EpisodeViewHolder(R.layout.item_character, parent){
            //todo onClick
        }

    inner class EpisodeViewHolder(resource: Int, parent: ViewGroup, onItemClick: (Episode) -> Unit) :
        BaseViewHolder<Episode>(
            resource,
            parent, onItemClick
        ) {

        private val textName: TextView = itemView.findViewById(R.id.item_text_episode_name)
        private val textDate: TextView = itemView.findViewById(R.id.item_text_date)
        private val textSeason: TextView = itemView.findViewById(R.id.item_text_season)
        private val textEpisodeNum: TextView = itemView.findViewById(R.id.item_text_episode_num)

        override fun bind(entity: Episode) {
            textName.text = entity.name
            textDate.text = entity.airDate
            textSeason.text = entity.getSeasonNum()
            textEpisodeNum.text = entity.getEpisodeNum()
        }
    }
}