package by.mankevich.rickandmorty.feature.base

import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.domain.base.IEntity

abstract class BaseAdapter<K: IEntity, T: BaseViewHolder<K>>(
    var entitiesList: List<K>
): RecyclerView.Adapter<T>() {

    override fun onBindViewHolder(holder: T, position: Int) {
        holder.bind(entitiesList[position])
    }

    override fun getItemCount(): Int = entitiesList.size

}