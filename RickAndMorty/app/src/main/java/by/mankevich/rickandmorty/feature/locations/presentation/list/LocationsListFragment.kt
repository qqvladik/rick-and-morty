package by.mankevich.rickandmorty.feature.locations.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.domain.locations.LocationEntity
import by.mankevich.rickandmorty.feature.base.InitUpdateViewService
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
        locationsAdapter = LocationsAdapter(emptyList())
        locationsDiffUtilCallback =
            LocationsDiffUtilCallback(locationsAdapter!!.entitiesList, emptyList())

        recyclerView = view.findViewById(R.id.recycler_list)

        /*recyclerView.layoutManager = GridLayoutManager(context, 2)
        val dividerItemDecorationVertical = DividerItemDecoration(context, RecyclerView.VERTICAL)
        dividerItemDecorationVertical.setDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.divider_drawable,
                null
            )!!
        )
        val dividerItemDecorationHorizontal =
            DividerItemDecoration(context, RecyclerView.HORIZONTAL)
        dividerItemDecorationHorizontal.setDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.divider_drawable,
                null
            )!!
        )
        recyclerView.addItemDecoration(dividerItemDecorationHorizontal)
        recyclerView.addItemDecoration(dividerItemDecorationVertical)*/

        InitUpdateViewService.getInstance().designRecyclerView(requireContext(), recyclerView, 2)

        recyclerView.adapter = locationsAdapter
    }

    private fun updateUI(
        locations: List<LocationEntity>
    ) {
        /*locationsDiffUtilCallback.oldList = locationsAdapter.entitiesList
        locationsDiffUtilCallback.newList = locations
        val contactsDiffResult = DiffUtil.calculateDiff(locationsDiffUtilCallback)
        locationsAdapter.entitiesList = locations
        contactsDiffResult.dispatchUpdatesTo(locationsAdapter)*/
        InitUpdateViewService.getInstance()
            .updateRecyclerView(locations, locationsAdapter!!, locationsDiffUtilCallback)
    }

    companion object {
        fun newInstance(): CharactersListFragment {
            return CharactersListFragment()
        }
    }
}