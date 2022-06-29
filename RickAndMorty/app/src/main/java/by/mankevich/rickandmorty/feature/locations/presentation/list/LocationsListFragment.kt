package by.mankevich.rickandmorty.feature.locations.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.library.db.entity.LocationEntity
import by.mankevich.rickandmorty.feature.base.UISupportService

class LocationsListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var locationsDiffUtilCallback: LocationsDiffUtilCallback
    private var locationsAdapter: LocationsAdapter? = null
    private val locationsListViewModel: LocationsListViewModel by lazy {
        ViewModelProvider(this).get(LocationsListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationsListViewModel.loadLocations()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        setHasOptionsMenu(true)
        initRecyclerView(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationsListViewModel.locationsLiveData.observe(
            viewLifecycleOwner
        ) { locations ->
            locations?.let {
                updateUI(locations)
            }
        }
    }

    private fun initRecyclerView(view: View) {
        locationsAdapter = LocationsAdapter(emptyList()) {
            UISupportService.showLocationDetailFragment(parentFragmentManager, it.id)
        }
        locationsDiffUtilCallback =
            LocationsDiffUtilCallback(locationsAdapter!!.entitiesList, emptyList())
        recyclerView = view.findViewById(R.id.recycler_list)
        UISupportService.designRecyclerView(requireContext(), recyclerView, 2)
        recyclerView.adapter = locationsAdapter
    }

    private fun updateUI(locations: List<LocationEntity>) {
        UISupportService.updateRecyclerView(
            locations,
            locationsAdapter!!,
            locationsDiffUtilCallback
        )
    }

    companion object {
        fun newInstance(): LocationsListFragment {
            return LocationsListFragment()
        }
    }
}