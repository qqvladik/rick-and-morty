package by.mankevich.rickandmorty.feature.base

import androidx.recyclerview.widget.DiffUtil
import by.mankevich.rickandmorty.domain.base.IEntity

abstract class BaseDiffUtilCallback<T: IEntity>(
    var oldList: List<T>,
    var newList: List<T>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

}