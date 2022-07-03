package by.mankevich.rickandmorty.feature.base

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.domain.base.IEntity

abstract class BaseAdapterByIds<K: IEntity, T: BaseViewHolder<K>>(
    var entitiesList: List<K>,
    diffUtilItemCallback: DiffUtil.ItemCallback<K>
): PagingDataAdapter<K, T>(diffUtilItemCallback) {

    override fun onBindViewHolder(holder: T, position: Int) {
        holder.bind(entitiesList[position])
    }

    override fun getItemCount(): Int = entitiesList.size

}