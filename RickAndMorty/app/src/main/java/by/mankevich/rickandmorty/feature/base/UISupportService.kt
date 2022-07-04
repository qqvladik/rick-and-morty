package by.mankevich.rickandmorty.feature.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.domain.base.IEntity
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
    }

    fun <T : IEntity, K : BaseViewHolder<T>> updateRecyclerView(
        entitiesList: List<T>,
        adapter: BaseAdapter<T, K>,
        diffUtilCallback: BaseDiffUtilCallback<T>
    ) {
        diffUtilCallback.oldList = adapter.entitiesList!!
        diffUtilCallback.newList = entitiesList
        val contactsDiffResult = DiffUtil.calculateDiff(diffUtilCallback)
        adapter.entitiesList = entitiesList
        contactsDiffResult.dispatchUpdatesTo(adapter)
    }

    fun showCharacterDetailFragment(fragmentManager: FragmentManager, characterId: Int) {
        Log.d(TAG, "showCharacterDetailFragment: ")
        val characterDetailFragment = CharacterDetailFragment.newInstance(characterId)
        showDetailFragment(fragmentManager, characterDetailFragment)
    }

    fun showEpisodeDetailFragment(fragmentManager: FragmentManager, episodeId: Int) {
        val episodeDetailFragment = EpisodeDetailFragment.newInstance(episodeId)
        showDetailFragment(fragmentManager, episodeDetailFragment)
    }

    fun showLocationDetailFragment(fragmentManager: FragmentManager, locationId: Int) {
        val locationDetailFragment = LocationDetailFragment.newInstance(locationId)
        showDetailFragment(fragmentManager, locationDetailFragment)
    }

    private fun showDetailFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            addToBackStack(null)
            commit()
        }
    }

//    fun getIsConnect(context: Context): Boolean {
//        var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi; 3: vpn
//        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            cm?.run {
//                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
//                    if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
//                        result = 2
//                    } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
//                        result = 1
//                    } else if (hasTransport(NetworkCapabilities.TRANSPORT_VPN)){
//                        result = 3
//                    }
//                }
//            }
//        } else {
//            cm?.run {
//                cm.activeNetworkInfo?.run {
//                    if (type == ConnectivityManager.TYPE_WIFI) {
//                        result = 2
//                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
//                        result = 1
//                    } else if(type == ConnectivityManager.TYPE_VPN) {
//                        result = 3
//                    }
//                }
//            }
//        }
//        return result != 0
//    }

}