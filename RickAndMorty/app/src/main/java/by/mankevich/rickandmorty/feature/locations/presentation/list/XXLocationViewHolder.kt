package by.mankevich.rickandmorty.feature.locations.presentation.list

import android.view.ViewGroup
import by.mankevich.rickandmorty.domain.locations.Location
import by.mankevich.rickandmorty.feature.base.BaseViewHolder

//todo возможно засунуть в адаптер
class XXLocationViewHolder(resource: Int, parent: ViewGroup, onItemClick: (Location) -> Unit) :
    BaseViewHolder<Location>(
        resource,
        parent,
        onItemClick
    ) {

    //todo
    /*private val textName: TextView = itemView.findViewById(R.id.item_text_character_name)
    private val textStatus: TextView = itemView.findViewById(R.id.item_text_status)
    private val textGender: TextView = itemView.findViewById(R.id.item_text_gender)
    private val imageView: ImageView = itemView.findViewById(R.id.item_character_image)*/

    override fun bind(entity: Location) {
        /*textName.text = entity.name
        textStatus.text = entity.status
        textGender.text = entity.gender
        Picasso.get()
            .load(entity.image)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_report_image)
            .centerCrop()
            .into(imageView)*/
    }
}