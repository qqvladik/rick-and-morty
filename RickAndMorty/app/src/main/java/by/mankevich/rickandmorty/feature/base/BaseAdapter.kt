package by.mankevich.rickandmorty.feature.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.domain.base.IEntity

abstract class BaseAdapter<K: IEntity, T: BaseViewHolder<K>>(
    var entities: List<K>
): RecyclerView.Adapter<T>() {

    override fun onBindViewHolder(holder: T, position: Int) {
        holder.bind(entities[position])
    }

    override fun getItemCount(): Int = entities.size

}