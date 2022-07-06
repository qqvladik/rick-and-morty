package by.mankevich.rickandmorty.feature.locations.presentation.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.feature.adapter.LocationsAdapter
import by.mankevich.rickandmorty.feature.base.BaseFragment
import by.mankevich.rickandmorty.feature.base.UISupportService
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "RAMLocationsList"

class LocationsListFragment : BaseFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var locationsPagingAdapter: LocationsAdapter
    private val locationsListViewModel: LocationsListViewModel by lazy {
        ViewModelProvider(this).get(LocationsListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        setHasOptionsMenu(true)
        initRecyclerView(view)
        initSwipeRefresh(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationsListViewModel.setIsConnect(isConnect())
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_filter -> {
                UISupportService.showFilterLocationsFragment(requireActivity().supportFragmentManager)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun initRecyclerView(view: View) {
        locationsPagingAdapter = LocationsAdapter {
            UISupportService.showLocationDetailFragment(
                requireActivity().supportFragmentManager,
                it.id
            )
        }
        locationsPagingAdapter.addLoadStateListener { loadStates ->
            if(loadStates.source.refresh is LoadState.NotLoading &&
                loadStates.append.endOfPaginationReached
            ) {
                if (locationsPagingAdapter.itemCount < 1) {
                    Toast.makeText(requireContext(), "No results", Toast.LENGTH_SHORT).show()
                }
            }else if(loadStates.source.refresh is LoadState.Error){
                val error = loadStates.source.refresh as LoadState.Error
                Toast.makeText(requireContext(), error.error.message, Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView = view.findViewById(R.id.recycler_list)
        UISupportService.designRecyclerView(requireContext(), recyclerView, 2)
        recyclerView.adapter = locationsPagingAdapter
    }

    private fun initSwipeRefresh(view: View) {
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = true
            locationsListViewModel.setIsConnect(isConnect())
            locationsPagingAdapter.refresh()
            swipeRefresh.isRefreshing = false
        }
    }

    companion object {
        fun newInstance(): LocationsListFragment {
            return LocationsListFragment()
        }
    }
}