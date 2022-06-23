package by.mankevich.rickandmorty.feature.base

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.domain.base.IEntity

class InitUpdateViewService private constructor() {
    companion object {
        fun getInstance(): InitUpdateViewService {
            return InitUpdateViewService()
        }
    }

    fun designRecyclerView(
        context: Context,
        recyclerView: RecyclerView,
        spanCount: Int
    ) {
        recyclerView.layoutManager = GridLayoutManager(context, spanCount)
        val dividerItemDecorationVertical = DividerItemDecoration(context, RecyclerView.VERTICAL)
        dividerItemDecorationVertical.setDrawable(
            ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.divider_drawable,
                null
            )!!
        )
        val dividerItemDecorationHorizontal =
            DividerItemDecoration(context, RecyclerView.HORIZONTAL)
        dividerItemDecorationHorizontal.setDrawable(
            ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.divider_drawable,
                null
            )!!
        )
        recyclerView.addItemDecoration(dividerItemDecorationHorizontal)
        recyclerView.addItemDecoration(dividerItemDecorationVertical)
    }

    fun <T: IEntity, K: BaseViewHolder<T>> updateRecyclerView(
        entitiesList: List<T>,
        adapter: BaseAdapter<T, K>,
        diffUtilCallback: BaseDiffUtilCallback<T>
    ) {
        diffUtilCallback.oldList = adapter.entitiesList
        diffUtilCallback.newList = entitiesList
        val contactsDiffResult = DiffUtil.calculateDiff(diffUtilCallback)
        adapter.entitiesList = entitiesList
        contactsDiffResult.dispatchUpdatesTo(adapter)
    }
}