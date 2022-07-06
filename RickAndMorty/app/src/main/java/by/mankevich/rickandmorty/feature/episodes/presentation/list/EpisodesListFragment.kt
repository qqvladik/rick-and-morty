package by.mankevich.rickandmorty.feature.episodes.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.feature.adapter.EpisodesAdapter
import by.mankevich.rickandmorty.feature.base.BaseFragment
import by.mankevich.rickandmorty.feature.base.UISupportService
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "RAMEpisodesListFragment"

class EpisodesListFragment : BaseFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var episodesPagingAdapter: EpisodesAdapter
    private val episodesListViewModel: EpisodesListViewModel by lazy {
        ViewModelProvider(this).get(EpisodesListViewModel::class.java)
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
        episodesListViewModel.setIsConnect(isConnect())
        lifecycleScope.launch {
            episodesListViewModel.data.collectLatest {
                episodesPagingAdapter.submitData(it)
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
                    episodesListViewModel.onSearchChanged(queryText)
                    return true
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_filter -> {
                UISupportService.showFilterEpisodesFragment(requireActivity().supportFragmentManager)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun initRecyclerView(view: View) {
        episodesPagingAdapter = EpisodesAdapter {
            UISupportService.showEpisodeDetailFragment(
                requireActivity().supportFragmentManager,
                it.id
            )
        }
        episodesPagingAdapter.addLoadStateListener { loadStates ->
            if(loadStates.source.refresh is LoadState.NotLoading &&
                loadStates.append.endOfPaginationReached
            ) {
                if (episodesPagingAdapter.itemCount < 1) {
                    Toast.makeText(requireContext(), "No results", Toast.LENGTH_SHORT).show()
                }
            }else if(loadStates.source.refresh is LoadState.Error){
                val error = loadStates.source.refresh as LoadState.Error
                Toast.makeText(requireContext(), error.error.message, Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView = view.findViewById(R.id.recycler_list)
        UISupportService.designRecyclerView(requireContext(), recyclerView, 2)
        recyclerView.adapter = episodesPagingAdapter
    }

    private fun initSwipeRefresh(view: View) {
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = true
            episodesListViewModel.setIsConnect(isConnect())
            episodesPagingAdapter.refresh()
            swipeRefresh.isRefreshing = false
        }
    }

    companion object {
        fun newInstance(): EpisodesListFragment {
            return EpisodesListFragment()
        }
    }
}