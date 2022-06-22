package by.mankevich.rickandmorty.feature.characters.presentation.list

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.domain.characters.Character
import by.mankevich.rickandmorty.feature.base.BaseAdapter
import by.mankevich.rickandmorty.feature.base.BaseViewHolder
import com.squareup.picasso.Picasso

class CharactersAdapter(characters: List<Character>) :
    BaseAdapter<Character, CharactersAdapter.CharacterViewHolder>(characters) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder(R.layout.item_character, parent){
            //todo onClick
        }

    inner class CharacterViewHolder(resource: Int, parent: ViewGroup, onItemClick: (Character) -> Unit) :
        BaseViewHolder<Character>(
            resource,
            parent, onItemClick
        ) {

        private val textName: TextView = itemView.findViewById(R.id.item_text_character_name)
        private val textStatus: TextView = itemView.findViewById(R.id.item_text_status)
        private val textGender: TextView = itemView.findViewById(R.id.item_text_gender)
        private val imageView: ImageView = itemView.findViewById(R.id.item_character_image)

        override fun bind(entity: Character) {
            textName.text = entity.name
            textStatus.text = entity.status
            textGender.text = entity.gender
            Picasso.get()
                .load(entity.image)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .centerCrop()
                .into(imageView)
        }
    }
}