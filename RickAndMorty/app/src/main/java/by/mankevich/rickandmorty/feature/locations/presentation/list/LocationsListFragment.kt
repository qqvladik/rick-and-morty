package by.mankevich.rickandmorty.feature.locations.presentation.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.*
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.feature.adapter.LocationsAdapter
import by.mankevich.rickandmorty.feature.base.BaseFragment
import by.mankevich.rickandmorty.feature.base.UISupportService
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "RAMLocationsList"

class LocationsListFragment : BaseFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var locationsPagingAdapter: LocationsAdapter
    private val locationsListViewModel: LocationsListViewModel by lazy {
        ViewModelProvider(this).get(LocationsListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: LocationsListFragment")
        locationsListViewModel.setIsConnect(isConnect())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: LocationsListFragment")
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_list_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(queryText: String): Boolean {
                    return true
                }

                override fun onQueryTextChange(queryText: String): Boolean {
                    Log.d(TAG, "QueryTextChange: $queryText")
                    locationsListViewModel.onSearchChanged(queryText)
                    return true
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: LocationsListFragment")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: LocationsListFragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: LocationsListFragment")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: LocationsListFragment")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach: LocationsListFragment")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach: LocationsListFragment")
    }

    private fun initRecyclerView(view: View) {
        locationsPagingAdapter = LocationsAdapter{
            UISupportService.showLocationDetailFragment(requireActivity().supportFragmentManager, it.id)
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