package by.mankevich.rickandmorty.feature.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.domain.base.IEntity

abstract class BaseViewHolder<T: IEntity>(
    @LayoutRes resource: Int,
    parent: ViewGroup,
    private val onItemClick: (T) -> Unit
): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(resource, parent, false)){

    init{
        itemView.setOnClickListener{
            onItemClick
        }
    }

    abstract fun bind(entity: T)
}