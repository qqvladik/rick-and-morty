package by.mankevich.rickandmorty.feature.base

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import by.mankevich.rickandmorty.domain.base.IEntity

/**
 * If entitiesList is null: BaseAdapterByIds will used as PagingDataAdapter,
 * if entitiesList is nonnull: BaseAdapterByIds will used as RecyclerView.Adapter
 */
abstract class BaseAdapter<K : IEntity, T : BaseViewHolder<K>>(
    var entitiesList: List<K>?,
    diffUtilItemCallback: DiffUtil.ItemCallback<K>
) : PagingDataAdapter<K, T>(diffUtilItemCallback) {

    override fun onBindViewHolder(holder: T, position: Int) {
        val item: K? = if (entitiesList == null) {
            getItem(position)
        } else {
            entitiesList!![position]
        }
        holder.bind(item!!)
    }

    override fun getItemCount(): Int {
        if (entitiesList != null) {
            return entitiesList!!.size
        }
        return super.getItemCount()
    }

}