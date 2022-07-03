package by.mankevich.rickandmorty.feature.adapter

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.library.db.entity.LocationEntity
import by.mankevich.rickandmorty.feature.base.BaseAdapter
import by.mankevich.rickandmorty.feature.base.BaseViewHolder

class LocationsAdapter(
    locations: List<LocationEntity>? = null,
    private var onItemClick: (LocationEntity) -> Unit
) :
    BaseAdapter<LocationEntity, LocationsAdapter.LocationViewHolder>(locations, DIFF_ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder =
        LocationViewHolder(R.layout.item_location, parent, onItemClick)


    inner class LocationViewHolder(
        resource: Int,
        parent: ViewGroup,
        onItemClick: (LocationEntity) -> Unit
    ) :
        BaseViewHolder<LocationEntity>(
            resource,
            parent,
            onItemClick
        ) {

        private val textName: TextView = itemView.findViewById(R.id.item_text_location_name)
        private val textType: TextView = itemView.findViewById(R.id.item_text_location_type)
        private val textDimension: TextView = itemView.findViewById(R.id.item_text_dimension)

        override fun bindView(entity: LocationEntity) {
            textName.text = entity.id.toString().plus(" ").plus(entity.name)
            textType.text = entity.type
            textDimension.text = entity.dimension
        }
    }

    companion object {
        val DIFF_ITEM_CALLBACK = object : DiffUtil.ItemCallback<LocationEntity>() {
            override fun areItemsTheSame(
                oldItem: LocationEntity,
                newItem: LocationEntity
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: LocationEntity,
                newItem: LocationEntity
            ): Boolean =
                oldItem.name == newItem.name &&
                oldItem.type == oldItem.type &&
                oldItem.dimension == oldItem.dimension &&
                oldItem.residents == oldItem.residents
        }
    }
}