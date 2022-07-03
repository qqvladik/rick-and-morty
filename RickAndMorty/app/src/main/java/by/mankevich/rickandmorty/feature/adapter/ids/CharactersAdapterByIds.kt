package by.mankevich.rickandmorty.feature.adapter.ids

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.feature.base.BaseAdapterByIds
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.feature.base.BaseViewHolder
import com.squareup.picasso.Picasso

class CharactersAdapterByIds(characters: List<CharacterEntity>, private var onItemClick: (CharacterEntity) -> Unit) :
    BaseAdapterByIds<CharacterEntity, CharactersAdapterByIds.CharacterViewHolder>(characters,
        DIFF_ITEM_CALLBACK
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder(R.layout.item_character, parent, onItemClick)


    inner class CharacterViewHolder(resource: Int, parent: ViewGroup, onItemClick: (CharacterEntity) -> Unit) :
        BaseViewHolder<CharacterEntity>(
            resource,
            parent, onItemClick
        ) {

        private val textName: TextView = itemView.findViewById(R.id.item_text_character_name)
        private val textStatus: TextView = itemView.findViewById(R.id.item_text_status)
        private val textGender: TextView = itemView.findViewById(R.id.item_text_gender)
        private val imageView: ImageView = itemView.findViewById(R.id.item_character_image)

        override fun bindView(entity: CharacterEntity) {
            textName.text = entity.name
            textStatus.text = entity.getStatusAndSpecies()
            textGender.text = entity.gender
            Picasso.get()
                .load(entity.image)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .fit()
                .into(imageView)
        }
    }

    companion object {
        val DIFF_ITEM_CALLBACK = object : DiffUtil.ItemCallback<CharacterEntity>() {
            override fun areItemsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean =
                oldItem.name == newItem.name &&
                        oldItem.status == newItem.status &&
                        oldItem.species == newItem.species &&
                        oldItem.type == newItem.type &&
                        oldItem.gender == newItem.gender &&
                        oldItem.image == newItem.image
        }
    }
}