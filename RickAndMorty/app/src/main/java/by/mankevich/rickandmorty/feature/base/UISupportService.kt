package by.mankevich.rickandmorty.feature.base

import android.content.Context
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.domain.base.IEntity
import by.mankevich.rickandmorty.domain.locations.LocationEntity
import by.mankevich.rickandmorty.feature.characters.presentation.detail.CharacterDetailFragment
import by.mankevich.rickandmorty.feature.episodes.presentation.detail.EpisodeDetailFragment
import by.mankevich.rickandmorty.feature.locations.presentation.detail.LocationDetailFragment

private const val TAG = "RAMUISupportService"

object UISupportService /*private constructor()*/ {
    /*companion object {
        fun getInstance(): UISupportService {
            return UISupportService()
        }
    }*/

    fun designRecyclerView(
        context: Context,
        recyclerView: RecyclerView,
        spanCount: Int
    ) {
        recyclerView.layoutManager = GridLayoutManager(context, spanCount)
//        val dividerItemDecorationVertical = DividerItemDecoration(context, RecyclerView.VERTICAL)
//        dividerItemDecorationVertical.setDrawable(
//            ResourcesCompat.getDrawable(
//                context.resources,
//                R.drawable.divider_drawable,
//                null
//            )!!
//        )
//        val dividerItemDecorationHorizontal =
//            DividerItemDecoration(context, RecyclerView.HORIZONTAL)
//        dividerItemDecorationHorizontal.setDrawable(
//            ResourcesCompat.getDrawable(
//                context.resources,
//                R.drawable.divider_drawable,
//                null
//            )!!
//        )
//        recyclerView.addItemDecoration(dividerItemDecorationHorizontal)
//        recyclerView.addItemDecoration(dividerItemDecorationVertical)
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

    fun showCharacterDetailFragment(fragmentManager: FragmentManager, characterId: Int){
        Log.d(TAG, "showCharacterDetailFragment: ")
        val characterDetailFragment = CharacterDetailFragment.newInstance(characterId)
        showDetailFragment(fragmentManager, characterDetailFragment)
    }

    fun showEpisodeDetailFragment(fragmentManager: FragmentManager, episodeId: Int){
        val episodeDetailFragment = EpisodeDetailFragment.newInstance(episodeId)
        showDetailFragment(fragmentManager, episodeDetailFragment)
    }

    fun showLocationDetailFragment(fragmentManager: FragmentManager, locationId: Int){
        val locationDetailFragment = LocationDetailFragment.newInstance(locationId)
        showDetailFragment(fragmentManager, locationDetailFragment)
    }

    private fun showDetailFragment(fragmentManager: FragmentManager, fragment: Fragment){
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            addToBackStack(null)
            commit()
        }
    }

}