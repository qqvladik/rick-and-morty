package by.mankevich.rickandmorty.feature.episodes.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import by.mankevich.rickandmorty.R
import by.mankevich.rickandmorty.feature.adapter.EpisodesAdapter
import by.mankevich.rickandmorty.feature.base.BaseFragment
import by.mankevich.rickandmorty.feature.base.UISupportService
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "RAMEpisodesListFragment"

class EpisodesListFragment : BaseFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var episodesPagingAdapter: EpisodesAdapter
    private val episodesListViewModel: EpisodesListViewModel by lazy {
        ViewModelProvider(this).get(EpisodesListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        episodesListViewModel.setIsConnect(isConnect())
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

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: EpisodesListFragment")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: EpisodesListFragment")
    }

    private fun initRecyclerView(view: View) {
        episodesPagingAdapter = EpisodesAdapter{
            UISupportService.showEpisodeDetailFragment(requireActivity().supportFragmentManager, it.id)
        }
        recyclerView = view.findViewById(R.id.recycler_list)
        UISupportService.designRecyclerView(requireContext(), recyclerView, 2)
        recyclerView.adapter = episodesPagingAdapter
    }

    companion object {
        fun newInstance(): EpisodesListFragment {
            return EpisodesListFragment()
        }
    }
}