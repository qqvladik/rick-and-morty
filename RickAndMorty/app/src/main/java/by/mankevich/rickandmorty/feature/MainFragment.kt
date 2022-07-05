package by.mankevich.rickandmorty.feature

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.feature.characters.presentation.list.CharactersListFragment
import by.mankevich.rickandmorty.feature.episodes.presentation.list.EpisodesListFragment
import by.mankevich.rickandmorty.feature.locations.presentation.list.LocationsListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val TAG = "RAMMainFragment"

private const val CHARACTERS_TAG = "CHARACTERS_TAG"
private const val EPISODES_TAG = "EPISODES_TAG"
private const val LOCATIONS_TAG = "LOCATIONS_TAG"

class MainFragment : Fragment() {

    private lateinit var bottomNavigationView: BottomNavigationView
//    private var isFirstLaunch = true

    private val charactersListFragment = CharactersListFragment.newInstance()
    private val locationsListFragment = LocationsListFragment.newInstance()
    private val episodesListFragment = EpisodesListFragment.newInstance()
    private var activeFragment: Fragment = charactersListFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: MainFragment")
        val view = inflater.inflate(R.layout.fragment_main, container, false)

//        if (isFirstLaunch) {
            childFragmentManager.beginTransaction().apply {
                if (childFragmentManager.findFragmentByTag(CHARACTERS_TAG) == null) {
                    Log.d(TAG, "childFragmentManager.findFragmentByTag(CHARACTERS_TAG) == null")
                    add(
                        R.id.fragment_nav_container,
                        charactersListFragment,
                        CHARACTERS_TAG
                    ).hide(charactersListFragment)
                }
                if (childFragmentManager.findFragmentByTag(LOCATIONS_TAG) == null) {
                    add(
                        R.id.fragment_nav_container,
                        locationsListFragment,
                        LOCATIONS_TAG
                    ).hide(locationsListFragment)
                }
                if (childFragmentManager.findFragmentByTag(EPISODES_TAG) == null) {
                    add(
                        R.id.fragment_nav_container,
                        episodesListFragment,
                        EPISODES_TAG
                    ).hide(episodesListFragment)
                }
                show(activeFragment)
            }.commit()
//            isFirstLaunch = false
//        }


        bottomNavigationView = view.findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.character_item -> {
                    childFragmentManager.beginTransaction().hide(activeFragment)
                        .show(charactersListFragment).commit()
                    activeFragment = charactersListFragment
                }
                R.id.locations_item -> {
                    childFragmentManager.beginTransaction().hide(activeFragment)
                        .show(locationsListFragment).commit()
                    activeFragment = locationsListFragment
                }
                R.id.episodes_item -> {
                    childFragmentManager.beginTransaction().hide(activeFragment)
                        .show(episodesListFragment).commit()
                    activeFragment = episodesListFragment
                }
            }
            true
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: MainFragment")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: MainFragment")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: MainFragment")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: MainFragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: MainFragment")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach: MainFragment")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach: MainFragment")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d(TAG, "onViewStateRestored: MainFragment")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: MainFragment")
    }

    private fun setCurrentFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_nav_container, fragment)
            commit()
        }
    }

}