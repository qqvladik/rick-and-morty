package by.mankevich.rickandmorty.feature.base

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.domain.base.IEntity

private const val TAG = "RAMBaseViewHolder"

abstract class BaseViewHolder<T: IEntity>(
    @LayoutRes resource: Int,
    parent: ViewGroup,
    private val onItemClick: (T) -> Unit
): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(resource, parent, false)){

    fun bind(entity: T){
        itemView.setOnClickListener{
            Log.d(TAG, "item Click ")
            onItemClick(entity)
        }
        bindView(entity)
    }

    protected abstract fun bindView(entity: T)
}