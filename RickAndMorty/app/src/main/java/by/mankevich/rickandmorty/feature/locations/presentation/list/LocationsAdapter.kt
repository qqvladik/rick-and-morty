package by.mankevich.rickandmorty.feature.locations.presentation.list

import android.view.ViewGroup
import android.widget.TextView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.domain.locations.LocationEntity
import by.mankevich.rickandmorty.feature.base.BaseAdapter
import by.mankevich.rickandmorty.feature.base.BaseViewHolder

class LocationsAdapter(locations: List<LocationEntity>, private var onItemClick: (LocationEntity) -> Unit) :
    BaseAdapter<LocationEntity, LocationsAdapter.LocationViewHolder>(locations) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder =
        LocationViewHolder(R.layout.item_character, parent, onItemClick)


    inner class LocationViewHolder(resource: Int, parent: ViewGroup, onItemClick: (LocationEntity) -> Unit) :
        BaseViewHolder<LocationEntity>(
            resource,
            parent,
            onItemClick
        ) {

        private val textName: TextView = itemView.findViewById(R.id.item_text_location_name)
        private val textType: TextView = itemView.findViewById(R.id.item_text_location_type)
        private val textDimension: TextView = itemView.findViewById(R.id.item_text_dimension)

        override fun bind(entity: LocationEntity) {
            textName.text = entity.name
            textType.text = entity.type
            textDimension.text = entity.dimension
        }
    }
}