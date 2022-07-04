package by.mankevich.rickandmorty.feature.locations.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.feature.adapter.LocationsAdapter
import by.mankevich.rickandmorty.feature.base.BaseFragment
import by.mankevich.rickandmorty.feature.base.UISupportService
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LocationsListFragment : BaseFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var locationsPagingAdapter: LocationsAdapter
    private val locationsListViewModel: LocationsListViewModel by lazy {
        ViewModelProvider(this).get(LocationsListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationsListViewModel.setIsConnect(isConnect())
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
        lifecycleScope.launch {
            locationsListViewModel.data.collectLatest {
                locationsPagingAdapter.submitData(it)
            }
        }
    }

    private fun initRecyclerView(view: View) {
        locationsPagingAdapter = LocationsAdapter{
            UISupportService.showLocationDetailFragment(parentFragmentManager, it.id)
        }
        recyclerView = view.findViewById(R.id.recycler_list)
        UISupportService.designRecyclerView(requireContext(), recyclerView, 2)
        recyclerView.adapter = locationsPagingAdapter
    }

    companion object {
        fun newInstance(): LocationsListFragment {
            return LocationsListFragment()
        }
    }
}