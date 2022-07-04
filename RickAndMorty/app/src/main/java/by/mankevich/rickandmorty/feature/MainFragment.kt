package by.mankevich.rickandmorty.feature

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

class MainFragment : Fragment() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val charactersListFragment = CharactersListFragment.newInstance()
        val locationsListFragment = LocationsListFragment.newInstance()
        val episodesListFragment = EpisodesListFragment.newInstance()

        setCurrentFragment(charactersListFragment)
        bottomNavigationView = view.findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.character_item -> setCurrentFragment(charactersListFragment)
                R.id.locations_item -> setCurrentFragment(locationsListFragment)
                R.id.episodes_item -> setCurrentFragment(episodesListFragment)
            }
            true
        }

        return view
    }

    private fun setCurrentFragment(fragment:Fragment) {
        childFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_nav_container, fragment)
            commit()
        }
    }

}