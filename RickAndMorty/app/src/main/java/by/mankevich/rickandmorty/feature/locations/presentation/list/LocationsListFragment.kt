package by.mankevich.rickandmorty.feature.locations.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.domain.locations.LocationEntity
import by.mankevich.rickandmorty.feature.base.UISupportService
import by.mankevich.rickandmorty.feature.characters.presentation.list.CharactersListFragment

class LocationsListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var locationsDiffUtilCallback: LocationsDiffUtilCallback
    private var locationsAdapter: LocationsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        setHasOptionsMenu(true)
        initRecyclerView(view)

        return view
    }

    private fun initRecyclerView(view: View) {
        locationsAdapter = LocationsAdapter(emptyList()){
            UISupportService.showLocationDetailFragment(parentFragmentManager, it.id)
        }
        locationsDiffUtilCallback = LocationsDiffUtilCallback(locationsAdapter!!.entitiesList, emptyList())
        UISupportService.designRecyclerView(requireContext(), recyclerView, 2)
        recyclerView.adapter = locationsAdapter
    }

    private fun updateUI(locations: List<LocationEntity>) {
        UISupportService.updateRecyclerView(locations, locationsAdapter!!, locationsDiffUtilCallback)
    }

    companion object {
        fun newInstance(): CharactersListFragment {
            return CharactersListFragment()
        }
    }
}