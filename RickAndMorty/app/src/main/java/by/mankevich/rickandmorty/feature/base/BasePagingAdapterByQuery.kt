package by.mankevich.rickandmorty.feature.base

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import by.mankevich.rickandmorty.domain.base.IEntity

abstract class BasePagingAdapterByQuery <K: IEntity, T: BaseViewHolder<K>>(

    diffUtilItemCallback: DiffUtil.ItemCallback<K>
): PagingDataAdapter<K, T>(diffUtilItemCallback) {

    override fun onBindViewHolder(holder: T, position: Int) {
        val item = getItem(position)
        holder.bind(item!!)
    }
}