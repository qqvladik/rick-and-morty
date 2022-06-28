package by.mankevich.rickandmorty.feature.locations.presentation.list

import by.mankevich.rickandmorty.library.db.LocationEntity
import by.mankevich.rickandmorty.feature.base.BaseDiffUtilCallback

class LocationsDiffUtilCallback(oldList: List<LocationEntity>,
                                newList: List<LocationEntity>
) : BaseDiffUtilCallback<LocationEntity>(oldList, newList) {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name &&
                oldList[oldItemPosition].type == newList[newItemPosition].type &&
                oldList[oldItemPosition].dimension == newList[newItemPosition].dimension &&
                oldList[oldItemPosition].residents == newList[newItemPosition].residents
    }
}